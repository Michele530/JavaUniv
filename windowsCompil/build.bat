@echo off
REM -------------------------------
REM Build JavaFX Project
REM -------------------------------

REM Chemin vers JavaFX SDK (relatif au projet)
SET JAVAFX_LIB=Projet\javafx-sdk-25.0.2\lib

REM Dossier des classes compilées
SET BIN_DIR=bin

REM Créer bin si nécessaire
IF NOT EXIST %BIN_DIR% (
    mkdir %BIN_DIR%
)

echo Compilation recursive...
for /R Projet %%f in (*.java) do (
    echo Compiling %%f
    javac --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.graphics -d %BIN_DIR% "%%f"
)

IF %ERRORLEVEL% NEQ 0 (
    echo.
    echo Erreur de compilation. Vérifie JavaFX et le code.
    pause
    exit /b 1
)

echo.
echo Compilation terminee avec succes !
pause
