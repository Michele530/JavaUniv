# Makefile pour TP2

# Dossiers
SRC_DIR := .
BIN_DIR := bin

# Packages
PKG := TP2

# Classes à compiler
SOURCES := $(wildcard $(SRC_DIR)/$(PKG)/*.java)
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
	@mkdir -p $(BIN_DIR)/$(PKG)
	$(JAVAC) -d $(BIN_DIR) $<

# Exécuter le test
run: all
	$(JAVA) -cp $(BIN_DIR) $(TEST_CLASS)

# Nettoyer les .class
clean:
	rm -rf $(BIN_DIR)/*

.PHONY: all run clean
