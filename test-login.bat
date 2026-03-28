@echo off
echo ========================================
echo   TEST LOGIN
echo ========================================

set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot"
echo JAVA_HOME: %JAVA_HOME%

cd quanLyBanHang

echo.
echo Dang rebuild SecurityConfig...
call mvnw.cmd clean compile -DskipTests

echo.
echo ========================================
echo   Starting app - XEM LOG BEN DUOI!
echo ========================================
echo.
echo Tim dong nay trong log:
echo   "SecurityConfig: Creating admin user"
echo.
echo Neu KHONG thay dong do = SecurityConfig chua duoc load
echo.
echo.

call mvnw.cmd spring-boot:run
