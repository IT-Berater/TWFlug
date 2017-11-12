FROM java:7
COPY . /usr/src/twflug
WORKDIR /usr/src/twflug
RUN javac src/main/java/de/wenzlaff/twflug/TWFlug.java
CMD ["java", "Main"]
