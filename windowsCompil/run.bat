@echo off
REM -------------------------------
REM Run JavaFX Project
REM -------------------------------

REM Chemin vers JavaFX SDK (relatif au projet)
SET JAVAFX_LIB=Projet\javafx-sdk-25.0.2\lib

REM Dossier des classes compilées
SET BIN_DIR=bin

REM Classe principale à lancer
SET MAIN_CLASS=Projet.ui.DeliveryVisualizerTest

echo Lancement de %MAIN_CLASS%...
java --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.graphics -cp %BIN_DIR% %MAIN_CLASS%

echo.
echo ===============================
echo Fin du programme
pause
