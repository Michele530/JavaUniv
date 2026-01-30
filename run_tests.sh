#!/bin/bash
# run_tests.sh
# Exécute les tests CrashTestGraph et TestGraphAlgo

PROJECT_DIR="$(pwd)/JavaUniv"
BIN_DIR="$PROJECT_DIR/bin"

cd "$PROJECT_DIR" || exit

echo "=== Lancement du test CrashTestGraph ==="
java -cp "$BIN_DIR" Projet.test.CrashTestGraph

echo ""
echo "=== Lancement du test TestGraphAlgo ==="
java -cp "$BIN_DIR" Projet.test.TestGraphAlgo

echo ""
echo "=== Lancement du test LocationNodeTest ==="
java -cp "$BIN_DIR" Projet.test.LocationNodeTest

echo ""
echo "=== Lancement du test DeliveryManagerTest ==="
java -cp "$BIN_DIR" Projet.test.DeliveryManagerTest
