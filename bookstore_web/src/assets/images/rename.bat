@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
set /p oldData= old:
set /p targetdata= new:
echo updateing

for /f "delims=" %%a in ('dir /b /a-d *%old%*') do
	set "oldName=%%a"
set "newName=!oldName:%oldData%=%targetData%!"
ren "!oldName!" "!newName!"
)

echo success!!
pause