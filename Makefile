EXAMPLE_DIR=example
BUILD_DIR=build
CLASS_DIR=$(BUILD_DIR)/classes/main
TEST_DIR=$(BUILD_DIR)/tmp/test

TEST_RESULT=$(TEST_DIR)/result.dot
EXAMPLE_TXT=$(EXAMPLE_DIR)/example.txt
EXAMPLE_DOT=$(EXAMPLE_DIR)/example.dot

all: test

svg: test
	dot -Tsvg -O $(EXAMPLE_DOT)

clean:
	rm -f *~ $(EXAMPLE_DIR)/*~
	rm -rf $(BUILD_DIR)

build:
	gradle build

test:	build
	mkdir -p $(TEST_DIR)
	java -cp $(CLASS_DIR) Main $(EXAMPLE_TXT) > $(TEST_RESULT)
	cmp $(EXAMPLE_DOT) $(TEST_RESULT)
