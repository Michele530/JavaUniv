#!/bin/bash
# setup_project.sh
# Clone le projet et compile tout (HTTPS pour compatibilité globale)

# --- Configuration ---
REPO_URL="https://github.com/Michele530/JavaUniv.git"  # <- HTTPS au lieu de SSH
PROJECT_DIR="$(pwd)/JavaUniv"                           # clone dans le dossier courant

# --- Cloner si nécessaire ---
if [ ! -d "$PROJECT_DIR" ]; then
    echo "Clonage du projet..."
    git clone "$REPO_URL" "$PROJECT_DIR"
else
    echo "Le projet existe déjà. Mise à jour..."
    cd "$PROJECT_DIR" || exit
    git pull
fi

# --- Compiler ---
cd "$PROJECT_DIR" || exit
echo "Compilation du projet..."
make clean
make all

echo "Setup terminé !"
