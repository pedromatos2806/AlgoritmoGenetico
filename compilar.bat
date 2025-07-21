@echo off
REM Script de Compilação do Algoritmo Genético
REM ==========================================

echo 🔨 COMPILANDO ALGORITMO GENÉTICO...
echo.

REM Limpar compilações anteriores
if exist "bin" (
    echo 🧹 Limpando compilações anteriores...
    rmdir /s /q "bin"
)

REM Criar pasta bin
mkdir bin

REM Compilar classes básicas primeiro
echo 📦 Compilando classes básicas...
javac -d bin src/Aula.java src/Cromossomo.java
if errorlevel 1 (
    echo ❌ Erro ao compilar classes básicas!
    pause
    exit /b 1
)

REM Compilar classes principais
echo 🧬 Compilando algoritmos genéticos...
javac -d bin -cp bin src/AlgoritmoGenetico.java
javac -d bin -cp bin src/AlgoritmoGeneticoOtimizado.java
javac -d bin -cp bin src/AlgoritmoGeneticoUltraEscalavel.java
if errorlevel 1 (
    echo ❌ Erro ao compilar algoritmos!
    pause
    exit /b 1
)

REM Compilar testes
echo 🧪 Compilando testes...
javac -d bin -cp bin src/TesteSimples.java
javac -d bin -cp bin src/TesteEscalabilidade.java
if errorlevel 1 (
    echo ❌ Erro ao compilar testes!
    pause
    exit /b 1
)

echo.
echo ✅ COMPILAÇÃO CONCLUÍDA COM SUCESSO!
echo.
echo 📋 ALGORITMOS DISPONÍVEIS:
echo    • AlgoritmoGenetico          (15 disciplinas)
echo    • AlgoritmoGeneticoOtimizado (150+ disciplinas)
echo    • AlgoritmoGeneticoUltraEscalavel (500+ disciplinas)
echo.
echo 🧪 TESTES DISPONÍVEIS:
echo    • TesteSimples
echo    • TesteEscalabilidade
echo.
echo 🚀 Para executar:
echo    java -cp bin [NomeDoAlgoritmo]
echo.
pause
