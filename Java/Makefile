# Makefile da versão Java do motor geométrico.
#
# Deve ser invocado a partir da RAIZ do projeto (onde está o shapes.txt),
# pois o programa lê "shapes.txt" relativo ao diretório de execução:
#
#   make -C Java run      # compila e executa
#   make -C Java          # apenas compila
#   make -C Java clean    # remove os .class
#
# Alternativa sem make:
#   javac -d Java/out $(find Java/src -name '*.java')
#   java -cp Java/out geo.app.Main

JAVAC   = javac
JAVA    = java
SRC_DIR = src/main/java
OUT_DIR = out
MAIN    = geo.app.Main
SHAPES  = ../shapes.txt

SOURCES = $(shell find $(SRC_DIR) -name '*.java')

# Marcador de compilação: regravado sempre que algum .java muda.
STAMP = $(OUT_DIR)/.compiled

all: $(STAMP)

$(STAMP): $(SOURCES)
	@mkdir -p $(OUT_DIR)
	$(JAVAC) -d $(OUT_DIR) $(SOURCES)
	@touch $(STAMP)

# Executa a partir da raiz do projeto para encontrar o shapes.txt.
run: $(STAMP)
	cd .. && $(JAVA) -cp Java/$(OUT_DIR) $(MAIN)

clean:
	rm -rf $(OUT_DIR)

.PHONY: all run clean
