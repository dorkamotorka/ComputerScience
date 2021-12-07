#!/usr/bin/python

alphabet_num = {
    0 : 'a',
    1 : 'b',
    2 : 'c',
    3 : 'd',
    4 : 'e',
    5 : 'f',
    6 : 'g',
    7 : 'h',
    8 : 'i',
    9 : 'j',
    10 : 'k',
    11 : 'l',
    12 : 'm',
    13 : 'n',
    14 : 'o',
    15 : 'p',
    16 : 'q',
    17 : 'r',
    18 : 's',
    19 : 't',
    20 : 'u',
    21 : 'v',
    22 : 'w',
    23 : 'x',
    24 : 'y',
    25 : 'z',
}

alphabet_chr = {
    'a': 0,
    'b': 1,
    'c': 2,
    'd': 3,
    'e': 4,
    'f': 5,
    'g': 6,
    'h': 7,
    'i': 8,
    'j': 9,
    'k': 10,
    'l': 11,
    'm': 12,
    'n': 13,
    'o': 14,
    'p': 15,
    'q': 16,
    'r': 17,
    's': 18,
    't': 19,
    'u': 20,
    'v': 21,
    'w': 22,
    'x': 23,
    'y': 24,
    'z': 25,
}

def encode_string(string):
    '''
    Map an integer to char
    '''
    encrypted = []
    for char in string:
        encrypted.append(str(encode(char)))

    return encrypted

def decode_string(string, mod=26):
    '''
    Map a char to integer
    '''
    decrypted = []
    for num in string:
        decrypted.append(decode(int(num), mod))

    return decrypted

def encode(char):
    return alphabet_chr[char]

def decode(num, mod=26):
    return alphabet_num[num % mod]

def shift_string(string, shift, mod=26):
    shifted = []
    for num in string:
        shifted.append(str(shift_cipher_enc(int(num), shift, mod)))

    return shifted

def deshift_string(string, shift, mod=26):
    deshifted = []
    for num in string:
        deshifted.append(str(shift_cipher_dec(int(num), shift, mod)))

    return deshifted

def shift_cipher_enc(num, shift, mod=26):
    return (num + shift) % mod

# Mod of negative numbers return positive in Python
def shift_cipher_dec(num, shift, mod=26):
    return (num - shift) % mod


if __name__ == '__main__':
    string = "heymynameisteo"
    shift = 3
    print("Original text: ", string)

    # ENCRYPTION
    encoded = encode_string(list(string))
    encrypted = shift_string(encoded, shift)
    decoded = decode_string(encrypted)
    stringed = ' '.join([str(elem) for elem in decoded])
    print("Encrypted text: ", stringed)

    # DECRYPTION
    undecoded = encode_string(decoded)
    decrypted = deshift_string(undecoded, shift) 
    unencoded = decode_string(decrypted)
    stringed = ' '.join([str(elem) for elem in unencoded])
    print("Decrypted text: ", stringed)
