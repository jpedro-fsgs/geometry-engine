CC = gcc
CFLAGS = -Wall -Wextra -std=c99 -Iinclude
LDFLAGS = -lm

SRC_DIR = src
OBJ_DIR = build
TARGET = geo_engine

# Lista manual de objetos para garantir ordem e controle
OBJ = $(OBJ_DIR)/main.o $(OBJ_DIR)/point.o $(OBJ_DIR)/line.o $(OBJ_DIR)/polygon.o \
      $(OBJ_DIR)/circle.o $(OBJ_DIR)/rectangle.o $(OBJ_DIR)/triangle.o $(OBJ_DIR)/operations.o

all: $(OBJ_DIR) $(TARGET)

$(OBJ_DIR):
	mkdir -p $(OBJ_DIR)

$(TARGET): $(OBJ)
	$(CC) $(CFLAGS) -o $@ $^ $(LDFLAGS)

$(OBJ_DIR)/%.o: $(SRC_DIR)/%.c
	$(CC) $(CFLAGS) -c $< -o $@

run: all
	cd .. && ./C/$(TARGET)

bench: all
	@echo "=== Benchmark C ==="
	@cd .. && bash -c 'time ./C/$(TARGET)'
	@echo ""

clean:
	rm -rf $(OBJ_DIR) $(TARGET)

.PHONY: all run bench clean

