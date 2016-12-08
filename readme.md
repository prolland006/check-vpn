# CheckVPN Documentation
## Summary
This tool check every x seconds your public ip adress, if it changes (vpn fall down for example) it stops one or several programs. When the VPN crash this tool can send a mail, play a sound.

## Requirements
- This tool work on Windows and Linux.
- You need to install JAVA 1,8 on your operating system

## Settings
- Download the .zip and unzip it
- Double click on checkvpn.jar
- Click on the button « Update ip » when the VPN is stopped to get your public ip address.
- Type the programs separated by semicolon that you would like to stop when the vpn is falling down (ex: utorrent.exe;firefox.exe on linux just put « firefox » no extension)
- Click on start button and the program will check every x seconds your ip address, if it not the vpn's ip the programs will be stopped by this tool.

## Options
- The option « auto start » start the process automaticaly when you start the tool without clicking on the button « start ».
- If you remove the .ser file you will have the default parameters.
- Put a wav to play a sound when the VPN crash or let it empty for no sound.
- Set the mail parameters to send an email when the VPN crash.
- You can send a command when the VPN crash


the web site [http://check.vpn.free.fr](http://check.vpn.free.fr)