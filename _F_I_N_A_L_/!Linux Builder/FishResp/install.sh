#!/bin/bash

if [ ! -x "/usr/bin/java" ]
then
	echo "You need to install JDK for using GUI and rJava lib" 
	exit 1	
fi

java_version=$(java -version 2>&1)
if [[ $java_version != *"JDK"* && $java_version != *"jdk"* && $java_version != *"Java(TM)"* ]] 
then
	echo "You need to install JDK for using GUI and rJava lib" 
	exit 1
fi

if [ -x "/usr/bin/Rscript" ]; then
	echo "Installing libraries..."
	echo "You need to have root permissions for rJava library configuration."
	if [ "$(sudo whoami)" != "root" ]; then
		echo "Sorry, you are not have root permissions"
		exit 1
	fi
	sudo R CMD javareconf
	sudo /usr/bin/Rscript ./installLibs.R 
	libjriLocation=$(sudo find /usr -name libjri.so)
	sudo cp $libjriLocation /usr/lib
	bashrcContent=$(cat ~/.bashrc)
	if [[ $bashrcContent != *"R_HOME"* ]] 
	then
		echo "R_HOME=\"/usr/lib/R\"" >> ~/.bashrc
	fi

	sudo cp -R FishResp /usr/lib/
	sudo chmod a+x /usr/lib/FishResp/FishRespBase.jar
	sudo chmod a+x /usr/lib/FishResp/FishResp.sh
	sudo cp starter /usr/bin/FishResp
	sudo chmod a+x /usr/bin/FishResp
	echo "Adding an icon to the menu"
	sudo cp FishResp.desktop /usr/share/applications/
	sudo chmod +x /usr/share/applications/FishResp.desktop
	sudo update-desktop-database
else
	echo "Please install R package first"
	exit 1
fi
 
exit 1;
