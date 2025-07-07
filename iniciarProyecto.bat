@echo off

start cmd /k "java -jar gestion\target\gestion-0.0.1-SNAPSHOT.jar"

start cmd /k "cloudflared tunnel run"

start cmd /k "curl parrot.live"

ferraMasDjango\env\Scripts\python.exe ferraMasDjango\manage.py runserver

pause