FROM openjdk
RUN mkdir /home/shortcut
WORKDIR /home/shortcut
ADD target/url-shortcut-0.1.jar url-shortcut.jar
ENTRYPOINT java -jar url-shortcut.jar