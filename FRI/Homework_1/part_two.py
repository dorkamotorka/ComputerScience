#!/usr/bin/env python

Em = 'weqrdxfbncqdsgqmisyrdvrntqeujqwyoqjoisncnffrbbesnapzgcjtrqrocczboiczxrvugboezzqjvzanuafbrmipvrbmehbdbrvhwbjanwbfguxwkvvviicrkckvrmqubbckfarkitycnzyocbafarjktwqnvkvnbjvtnutvburujovmbysxrvxoambysanfkrnhpvgrwbfigcefabamyiacadsafpfkramkcfniiquowivvvjlhgqmphbxakolnlrknhbysaxvkvrcpzfqmipvrbmehsxzrzyqqjvhwbjarwierfjqugpxcihuneycynnffrbbkvexcxvnwlucaxbxwincgiacqcmrqimssxcernutkvenmsigxnkvrbmrzfxvfbrlidsuxuvotjqeoamwwhunxrqxxnychwljkurkyhungyoqcibsafqkvgqmdbbwmnsenavsavwissawdhujbkwznnffgqvfcanefiymiemyxvxseemehhamzbgxbyssxzvggjvuwguiphunzvwammvdfcqczanajoamafzvccusnwlecgqqeujjajsrwwwwgkckgbvmkwznarbrjocsbaiyojtncmvwofjraqk'


subs = ['mipvrbmeh', 'nffrbb', 'vhwbja', 'qeuj', 'bysa', 'kwzn']#, 'sxz', 'saf', 'nut', 'nwl', 'nkv', 'wbf', 'www', 'yoq', 'cgq', 'ncm', 'gqm', 'oam', 'jvu', 'xrv', 'fbr', 'kvr', 'vvv', 'far', 'fkr', 'hun']
out = []
for sub in subs:
    if Em.count(sub) > 1:
        print(Em.count(sub))
        out.append(sub)

print(out)

# return starting index of word
starts = []
for sub in out:
    start = Em.find(sub)
    starts.append(start)
    start2 = Em.find(sub, start + 1)
    if start2 != -1:
        starts.append(start2)
    else: 
        continue
    start3 = Em.find(sub, start2 + 1)
    if start3 != -1:
        starts.append(start3)
    else: 
        continue
    start4 = Em.find(sub, start3 + 1)
    if start4 != -1:
        starts.append(start4)

print(starts)

# Distance between words repetition - calculated from starts array
distances = [279-89, 325-40, 297-102, 570-25, 265-190, 591-476]
print(distances)

# Greatest common divisor is 5
# [190, 285, 195, 545, 75, 115]
# 190 = 5 x 38 = 2 x 5 x 19 
# 285 = 5 x 57
# 195 = 5 x 3 x 13
# 545 = 5 x 109
# 75 = 5 x 3 x 5
# 115 = 5 x 23

# From repetitions it is obvious that the key length is 5

# Since key length is 5, we can divide text on sections of length 5 and analyze it as monoalphabetic cipher
length = 5
strings = [Em[i:i+length] for i in range(0, len(Em), length)]
print(strings)

etable = {
    'a' : 'n',
    'b' : 'o',
    'c' : 'p',
    'd' : 'q',
    'e' : 'r',
    'f' : 's',
    'g' : 't',
    'h' : 'u',
    'i' : 'v',
    'j' : 'w',
    'k' : 'x',
    'l' : 'y',
    'm' : 'z',
    'n' : 'a',
    'o' : 'b',
    'p' : 'c',
    'q' : 'd',
    'r' : 'e',
    's' : 'f',
    't' : 'g',
    'u' : 'h',
    'v' : 'i',
    'w' : 'j',
    'x' : 'k',
    'y' : 'l',
    'z' : 'm',
}

first = []
second = []
third = []
forth = []
fifth = []

for s in strings:
    try:
        first.append(s[0])
        second.append(s[1])
        third.append(s[2])
        forth.append(s[3])
        fifth.append(s[4])
    except:
        pass

combined = []
combined.append(first)
combined.append(second)
combined.append(third)
combined.append(forth)
combined.append(fifth)
print(combined)

for a in combined:
    print(a)
    print("------------------------------")
    for f in etable:
        num = a.count(f)
        if num != 0:
            print(f + ' repeats ' + str(num))

# Most repeated cipher letters
# 1: b, i, m, q
# 2: f, k, y
# 3: s, v, o, h 
# 4: r, a, n, g
# 5: n, x, w, j, c

# Calculated as: cipher_letter = letter + key
# Most common letter is E(5)
'''
m - e = 8 -> i
---
v - e = 18 -> r  
---
s - e = 14 -> o
---
r - e = 13 -> n
---
n - e = 9 -> j
'''

# key = IRONJ
