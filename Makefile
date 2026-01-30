# Makefile pour TP2 - version récursive

# Dossiers
SRC_DIR := .
BIN_DIR := bin

# Packages
PKG := Projet

# Chemin vers JavaFX portable
JAVAFX_LIB := Projet/javafx-sdk-25/lib
JAVAFX_MODULES := javafx.controls,javafx.graphics


# Classes à compiler (récursivement)
SOURCES := $(shell find $(SRC_DIR)/$(PKG) -name "*.java")
CLASSES := $(patsubst $(SRC_DIR)/%.java,$(BIN_DIR)/%.class,$(SOURCES))

# Commandes
JAVAC := javac
JAVA := java

# La classe de test par défaut
TEST_CLASS := $(PKG).TestGraphAlgo

# -------------------------------
# Règle par défaut : compile tout
all: $(CLASSES)

# Compiler les .java dans bin/
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	@mkdir -p $(dir $@)
	$(JAVAC) -d $(BIN_DIR) $<

# Exécuter le test
run: all
	$(JAVA) -cp $(BIN_DIR) $(TEST_CLASS)

# Exécuter l'appli JavaFX
runfx: all
	$(JAVA) --module-path $(JAVAFX_LIB) --add-modules $(JAVAFX_MODULES) -cp $(BIN_DIR) Projet.ui.DeliveryVisualizerTest


# Nettoyer les .class
clean:
	rm -rf $(BIN_DIR)/*

.PHONY: all run clean

