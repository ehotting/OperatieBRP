for /f %%i in ('docker ps -a -q') do docker stop %%i