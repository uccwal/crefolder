@echo off
set folderPath=C:\Users\user\Desktop\ExampleFolder

if not exist "%folderPath%" (
    mkdir "%folderPath%"
    echo 폴더가 성공적으로 생성되었습니다.
) else (
    echo 폴더가 이미 존재합니다.
)