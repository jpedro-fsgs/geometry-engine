# Makefile Principal do Repositório

.PHONY: all clean run-c run-java bench-c bench-java bench

all:
	@echo "Compilando módulo em C..."
	$(MAKE) -C C
	@echo "Compilando módulo em Java..."
	$(MAKE) -C Java

run-c:
	@echo "Executando implementação em C..."
	$(MAKE) -C C run

run-java:
	@echo "Executando implementação em Java..."
	$(MAKE) -C Java run

bench-c:
	$(MAKE) -C C bench

bench-java:
	$(MAKE) -C Java bench

bench: bench-c bench-java

clean:
	@echo "Limpando arquivos gerados..."
	$(MAKE) -C C clean
	$(MAKE) -C Java clean

