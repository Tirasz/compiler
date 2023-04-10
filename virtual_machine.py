from sys import argv
from pathlib import Path

OPCODE_LEN = 4
PUSH_LEN_FIELD_LEN = 6
MAX_MEMORY = 1024
STACK = []
RAM = [0] * MAX_MEMORY
REGISTER_A = REGISTER_B = 0

def push(value): # 
    print(f"PUSHING: {value}")
    STACK.append(value)

def _push_wrapper(value):
    def _push():
        push(value)
    return _push

def add(): # 
    REGISTER_B = STACK.pop()
    REGISTER_A = STACK.pop()
    push(REGISTER_A + REGISTER_B)

def sub(): # 
    REGISTER_B = STACK.pop()
    REGISTER_A = STACK.pop()
    push(REGISTER_A - REGISTER_B)

def mul(): # 
    REGISTER_B = STACK.pop()
    REGISTER_A = STACK.pop()
    push(REGISTER_A * REGISTER_B)

def div(): # 
    REGISTER_B = STACK.pop()
    REGISTER_A = STACK.pop()
    push(REGISTER_A // REGISTER_B)

def mod(): # 
    REGISTER_B = STACK.pop()
    REGISTER_A = STACK.pop()
    push(REGISTER_A % REGISTER_B)

def pwr(): #
    REGISTER_B = STACK.pop()
    REGISTER_A = STACK.pop()
    push(REGISTER_A ** REGISTER_B)

def mema(): # 
    REGISTER_B = STACK.pop()
    REGISTER_A = STACK.pop()
    if(REGISTER_A >= MAX_MEMORY):
        raise Exception("Not enough memory!")
    RAM[REGISTER_A] = REGISTER_B

def memr(): # 
    REGISTER_A = STACK.pop()
    if(REGISTER_A >= MAX_MEMORY):
        raise Exception("Not enough memory!")
    push(RAM[REGISTER_A])

# read(x) = m[x] = input() --> push x, push input, mema
def inp(): # 
    REGISTER_A = int(input("Input an integer"))
    push(REGISTER_A)

def _print(): # 
    REGISTER_A = STACK.pop()
    print(REGISTER_A)
    if(len(STACK) > 0):
      print("WARGNING: Stack not empty after eval")

INSTRUCTIONS = {
    0: _push_wrapper,
    1: add,
    2: sub,
    3: mul,
    4: div,
    5: mod,
    6: pwr,
    7: mema,
    8: memr,
    9: inp,
    10: _print   
}

def _parse_opcode(bytecode):
    opcode_str = bytecode[0:OPCODE_LEN]
    if(not len(opcode_str) == 4):
        raise Exception(f"Unfinished opcode: {opcode_str}")
    
    opcode = int(opcode_str, 2)
    if(not opcode in INSTRUCTIONS.keys()):
        raise Exception(f"Not recognized opcode: {opcode}")
    
    instruction = INSTRUCTIONS[opcode]
    parsed_bits = OPCODE_LEN

    if(opcode == 0):
        payload_len = int(bytecode[parsed_bits : parsed_bits + PUSH_LEN_FIELD_LEN], 2)
        parsed_bits += PUSH_LEN_FIELD_LEN
        value = int(bytecode[parsed_bits : parsed_bits + payload_len], 2)
        parsed_bits += payload_len
        instruction = instruction(value)

    return (parsed_bits, instruction)
            



def run_bytecode(bytecode):
    # The file will contain "bytecode": a series of 'opcodes', in binary 
    # The only 'opcode' with additional parameters will be push (0000):
    # following it will be 6 bits for the length of the 'payload' and the payload itself (the number to push)
    # (6 bits because i guess thats the largest number java can handle probably (64 bit number))
    i = 0
    while i < len(bytecode):
        (parsed_bits, instruction) = _parse_opcode(bytecode[i:])
        i += parsed_bits
        instruction()
    

def main():
    if(len(argv) == 1):
        print("Error: No input file provided!")
        return
    elif (len(argv) > 2):
        print("Error: Too many arguments!")
        return
    
    path = Path(argv[1]).resolve()
    if not path.exists():
        print("Error: Input file does not exists!")
        return
    
    with open(path) as src:
        run_bytecode(src.read())
    
if __name__ == "__main__":
    main()
