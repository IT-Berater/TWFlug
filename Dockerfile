# Verwende das folgende Debian Package:
FROM debian:9.2 

# Meine Kontakt Daten;
MAINTAINER Thomas Wenzlaff, Langenhagen, Germany, info-anfrage@wenzlaff.de

# Nötige Settings für die Umgebung:
RUN sed -i 's/exit 101/exit 0/g' /usr/sbin/policy-rc.d
ENV DEBIAN_FRONTEND noninteractive 

# Installation der nötigen Packages:
RUN apt-get update && apt-get install -y \
	apt-utils \
	cron      \
	curl	  \
	dialog    \
 	git       \
  lighttpd  \
	netcat	  \
	net-tools \
	python2.7 \
	wget
  
# Update aller vorhandener Packages
RUN apt-get update && apt-get upgrade -y

# Starte die bash
CMD ["/bin/bash"]
