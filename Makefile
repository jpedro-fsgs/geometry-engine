CC = gcc
CFLAGS = -Wall -Wextra -std=c99
LDFLAGS = -lm

OBJ = main.o point.o line.o polygon.o circle.o rectangle.o triangle.o
TARGET = geo_engine

all: $(TARGET)

$(TARGET): $(OBJ)
	$(CC) $(CFLAGS) -o $@ $^ $(LDFLAGS)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f $(OBJ) $(TARGET)
