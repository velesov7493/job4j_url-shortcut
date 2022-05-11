package ru.job4j.urlshortcut.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.domains.Site;
import ru.job4j.urlshortcut.domains.Url;
import ru.job4j.urlshortcut.dto.*;
import ru.job4j.urlshortcut.exceptions.JwtAuthenticationException;
import ru.job4j.urlshortcut.exceptions.ObjectNotFoundException;
import ru.job4j.urlshortcut.exceptions.OperationNotAcceptableException;
import ru.job4j.urlshortcut.security.JwtTokenProvider;
import ru.job4j.urlshortcut.services.SiteService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SiteController {

    private final SiteService sites;
    private final JwtTokenProvider tokens;

    public SiteController(SiteService service, JwtTokenProvider provider) {
        sites = service;
        tokens = provider;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegisterRespDto> register(@RequestBody SiteDto site)
        throws OperationNotAcceptableException {

        Site s = sites.findByLogin(site.getSite());
        RegisterRespDto result;
        HttpStatus status;
        if (s != null) {
            result = RegisterRespDto.of(true, s);
            status = HttpStatus.OK;
        } else {
            s = new Site();
            s.setLogin(site.getSite());
            Site n = sites.saveSite(s);
            result = RegisterRespDto.of(false, n);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(result, status);
    }

    @PostMapping("/convert")
    public ResponseEntity<CodeRespDto> convert(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody UrlDto url
    ) throws JwtAuthenticationException, OperationNotAcceptableException {

        Site site = tokens.getSite(token);
        Url u = sites.findUrlByLink(url.getUrl());
        if (u == null) {
            u = new Url();
            u.setSiteId(site.getId());
            u.setLink(url.getUrl());
            u = sites.saveUrl(u);
        }
        CodeRespDto resp = new CodeRespDto();
        resp.setCode(u.getCode());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code)
        throws ObjectNotFoundException {

        Url u = sites.findUrlAndIncrementCallsByCode(code);
        if (u == null) {
            throw new ObjectNotFoundException();
        }
        return
                ResponseEntity.status(301)
                .location(URI.create(u.getLink()))
                .build();
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<StatisticDto>> statistic(
        @RequestHeader(name = "Authorization") String token
    ) throws JwtAuthenticationException {

        ResponseEntity<List<StatisticDto>> result;
        Site site = tokens.getSite(token);
        List<Url> list = sites.findAllUrlBySiteId(site.getId());
        if (list != null && !list.isEmpty()) {
            List<StatisticDto> statisticDtoList =
                    list.stream()
                    .map(StatisticDto::of)
                    .collect(Collectors.toList());
            result = new ResponseEntity<>(statisticDtoList, HttpStatus.OK);
        } else {
            result = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return result;
    }
}
