@echo off
REM Script de Execução do Algoritmo Genético
REM ========================================

:MENU
cls
echo.
echo 🧬 ALGORITMO GENÉTICO - AGENDAMENTO UNIVERSITÁRIO
echo ================================================
echo.
echo Escolha a versão para executar:
echo.
echo 1. 📚 Versão Educacional (15 disciplinas)
echo 2. 🏢 Versão Otimizada (150+ disciplinas)  
echo 3. 🏭 Versão Ultra Escalável (500+ disciplinas)
echo 4. 🧪 Teste de Escalabilidade
echo 5. ✅ Teste Simples
echo 6. 🔨 Recompilar Tudo
echo 0. ❌ Sair
echo.
set /p opcao="Digite sua opção (0-6): "

if "%opcao%"=="1" goto EDUCACIONAL
if "%opcao%"=="2" goto OTIMIZADA
if "%opcao%"=="3" goto ULTRA
if "%opcao%"=="4" goto TESTE_ESCALA
if "%opcao%"=="5" goto TESTE_SIMPLES
if "%opcao%"=="6" goto RECOMPILAR
if "%opcao%"=="0" goto SAIR

echo ❌ Opção inválida!
timeout /t 2 >nul
goto MENU

:EDUCACIONAL
echo.
echo 📚 Executando Versão Educacional...
echo ===================================
java -cp bin AlgoritmoGenetico
echo.
pause
goto MENU

:OTIMIZADA
echo.
echo 🏢 Executando Versão Otimizada...
echo =================================
java -cp bin AlgoritmoGeneticoOtimizado
echo.
pause
goto MENU

:ULTRA
echo.
echo 🏭 Executando Versão Ultra Escalável...
echo ======================================
echo ⚠️  Esta versão usa mais memória RAM
java -Xmx2G -cp bin AlgoritmoGeneticoUltraEscalavel
echo.
pause
goto MENU

:TESTE_ESCALA
echo.
echo 🧪 Executando Teste de Escalabilidade...
echo ========================================
java -cp bin TesteEscalabilidade
echo.
pause
goto MENU

:TESTE_SIMPLES
echo.
echo ✅ Executando Teste Simples...
echo ==============================
java -cp bin TesteSimples
echo.
pause
goto MENU

:RECOMPILAR
echo.
echo 🔨 Recompilando projeto...
echo ==========================
call compilar.bat
goto MENU

:SAIR
echo.
echo 👋 Obrigado por usar o Algoritmo Genético!
echo.
exit /b 0
