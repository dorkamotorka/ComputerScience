# Exercise 2

![image](https://user-images.githubusercontent.com/48418580/137532345-6aa550e7-ef2b-4de4-8d94-c032ac5c10bd.png)

## First step: Find Repeating Substrings

For key search we will only take into an account the substrings with at least 4 letters.
Which are: 

	['mipvrbmeh', 'nffrbb', 'vhwbja', 'qeuj', 'bysa', 'kwzn']

## Second step: Calculate the distance between two equal repeated substrings

We know the maximum times string has repeated is 4-times.
	
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

According to the starting index of substrings we can calculate distances:

	[190, 285, 195, 545, 75, 115]		

## Third step: Find Greatest Common Divisor == Key Length

	190 = 5 x 38 = 2 x 5 x 19
	285 = 5 x 57
	195 = 5 x 3 x 13
	545 = 5 x 109
	75 = 5 x 3 x 5
	115 = 5 x 23

From the multiples we see that 5 repeats the most and we conclude this is the key length.

## Fourth step: Separate message on key length strings

	['weqrd', 'xfbnc', 'qdsgq', 'misyr', 'dvrnt', 'qeujq', 'wyoqj', 'oisnc', 'nffrb', 'besna', 'pzgcj', 'trqro', 'cczbo', 'iczxr', 'vugbo', 'ezzqj', 'vzanu', 
	 'afbrm', 'ipvrb', 'mehbd', 'brvhw', 'bjanw', 'bfgux', 'wkvvv', 'iicrk', 'ckvrm', 'qubbc', 'kfark', 'itycn', 'zyocb', 'afarj', 'ktwqn', 'vkvnb', 'jvtnu', 
	 'tvbur', 'ujovm', 'bysxr', 'vxoam', 'bysan', 'fkrnh', 'pvgrw', 'bfigc', 'efaba', 'myiac', 'adsaf', 'pfkra', 'mkcfn', 'iiquo', 'wivvv', 'jlhgq', 'mphbx', 
	 'akoln', 'lrknh', 'bysax', 'vkvrc', 'pzfqm', 'ipvrb', 'mehsx', 'zrzyq', 'qjvhw', 'bjarw', 'ierfj', 'qugpx', 'cihun', 'eycyn', 'nffrb', 'bkvex', 'cxvnw', 
	 'lucax', 'bxwin', 'cgiac', 'qcmrq', 'imssx', 'cernu', 'tkven', 'msigx', 'nkvrb', 'mrzfx', 'vfbrl', 'idsux', 'uvotj', 'qeoam', 'wwhun', 'xrqxx', 'nychw', 
	 'ljkur', 'kyhun', 'gyoqc', 'ibsaf', 'qkvgq', 'mdbbw', 'mnsen', 'avsav', 'wissa', 'wdhuj', 'bkwzn', 'nffgq', 'vfcan', 'efiym', 'iemyx', 'vxsee', 'mehha', 
	 'mzbgx', 'byssx', 'zvggj', 'vuwgu', 'iphun', 'zvwam', 'mvdfc', 'qczan', 'ajoam', 'afzvc', 'cusnw', 'lecgq', 'qeujj', 'ajsrw', 'wwwgk', 'ckgbv', 'mkwzn', 
	 'arbrj', 'ocsba', 'iyojt', 'ncmvw', 'ofjra', 'qk']


## Fifth step: Combine first/second/third/fourth/fifth letter

	['w', 'x', 'q', 'm', 'd', 'q', 'w', 'o', 'n', 'b', 'p', 't', 'c', 'i', 'v', 'e', 'v', 'a', 'i', 'm', 'b', 'b', 'b', 'w', 'i', 'c', 'q', 'k', 'i', 'z', 'a', 'k', 'v', 'j', 't', 'u', 'b', 'v', 'b', 'f', 'p', 'b', 'e', 'm', 'a', 'p', 'm', 'i', 'w', 'j', 'm', 'a', 'l', 'b', 'v', 'p', 'i', 'm', 'z', 'q', 'b', 'i', 'q', 'c', 'e', 'n', 'b', 'c', 'l', 'b', 'c', 'q', 'i', 'c', 't', 'm', 'n', 'm', 'v', 'i', 'u', 'q', 'w', 'x', 'n', 'l', 'k', 'g', 'i', 'q', 'm', 'm', 'a', 'w', 'w', 'b', 'n', 'v', 'e', 'i', 'v', 'm', 'm', 'b', 'z', 'v', 'i', 'z', 'm', 'q', 'a', 'a', 'c', 'l', 'q', 'a', 'w', 'c', 'm', 'a', 'o', 'i, 'n', 'o', 'q']
	['e', 'f', 'd', 'i', 'v', 'e', 'y', 'i', 'f', 'e', 'z', 'r', 'c', 'c', 'u', 'z', 'z', 'f', 'p', 'e', 'r', 'j', 'f', 'k', 'i', 'k', 'u', 'f', 't', 'y', 'f', 't', 'k', 'v', 'v', 'j', 'y', 'x', 'y', 'k', 'v', 'f', 'f', 'y', 'd', 'f', 'k', 'i', 'i', 'l', 'p', 'k', 'r', 'y', 'k', 'z', 'p', 'e', 'r', 'j', 'j', 'e', 'u', 'i', 'y', 'f', 'k', 'x', 'u', 'x', 'g', 'c', 'm', 'e', 'k', 's', 'k', 'r', 'f', 'd', 'v', 'e', 'w', 'r', 'y', 'j', 'y', 'y', 'b', 'k', 'd', 'n', 'v', 'i', 'd', 'k', 'f', 'f', 'f', 'e', 'x', 'e', 'z', 'y', 'v', 'u', 'p', 'v', 'v', 'c', 'j', 'f', 'u', 'e', 'e', 'j', 'w', 'k', 'k', 'r', 'c', 'y', 'c', 'f', 'k']
 	['q', 'b', 's', 's', 'r', 'u', 'o', 's', 'f', 's', 'g', 'q', 'z', 'z', 'g', 'z', 'a', 'b', 'v', 'h', 'v', 'a', 'g', 'v', 'c', 'v', 'b', 'a', 'y', 'o', 'a', 'w', 'v','t', 'b', 'o', 's', 'o', 's', 'r', 'g', 'i', 'a', 'i', 's', 'k', 'c', 'q', 'v', 'h', 'h', 'o', 'k', 's', 'v', 'f', 'v', 'h', 'z', 'v', 'a', 'r', 'g', 'h', 'c', 'f', 'v', 'v', 'c', 'w', 'i', 'm', 's', 'r', 'v', 'i', 'v', 'z', 'b', 's', 'o', 'o', 'h', 'q', 'c', 'k', 'h', 'o', 's', 'v', 'b', 's', 's', 's', 'h', 'w', 'f', 'c', 'i', 'm', 's', 'h', 'b', 's', 'g', 'w', 'h', 'w', 'd', 'z', 'o', 'z', 's', 'c', 'u', 's', 'w', 'g', 'w', 'b', 's', 'o', 'm', 'j']
	['r', 'n', 'g', 'y', 'n', 'j', 'q', 'n', 'r', 'n', 'c', 'r', 'b', 'x', 'b', 'q', 'n', 'r', 'r', 'b', 'h', 'n', 'u', 'v', 'r', 'r', 'b', 'r', 'c', 'c', 'r', 'q', 'n', 'n', 'u', 'v', 'x', 'a', 'a', 'n', 'r', 'g', 'b', 'a', 'a', 'r', 'f', 'u', 'v', 'g', 'b', 'l', 'n', 'a', 'r', 'q', 'r', 's', 'y', 'h', 'r', 'f', 'p', 'u', 'y', 'r', 'e', 'n', 'a', 'i', 'a','r', 's', 'n', 'e', 'g', 'r', 'f', 'r', 'u', 't', 'a', 'u', 'x', 'h', 'u', 'u', 'q', 'a', 'g', 'b', 'e', 'a', 's', 'u', 'z', 'g', 'a', 'y', 'y', 'e', 'h', 'g', 's', 'g', 'g', 'u', 'a', 'f', 'a', 'a', 'v', 'n', 'g', 'j', 'r', 'g', 'b', 'z', 'r', 'b', 'j', 'v', 'r']
	['d', 'c', 'q', 'r', 't', 'q', 'j', 'c', 'b', 'a', 'j', 'o', 'o', 'r', 'o', 'j', 'u', 'm', 'b', 'd', 'w', 'w', 'x', 'v', 'k', 'm', 'c', 'k','n', 'b', 'j', 'n', 'b', 'u', 'r', 'm', 'r', 'm', 'n', 'h', 'w', 'c', 'a', 'c', 'f', 'a', 'n', 'o', 'v', 'q', 'x', 'n', 'h', 'x', 'c', 'm', 'b', 'x', 'q', 'w', 'w', 'j', 'x', 'n', 'n', 'b', 'x', 'w', 'x', 'n', 'c', 'q', 'x', 'u', 'n', 'x', 'b', 'x', 'l', 'x', 'j', 'm', 'n', 'x', 'w', 'r', 'n', 'c', 'f', 'q', 'w', 'n', 'v', 'a', 'j', 'n', 'q', 'n', 'm', 'x', 'e', 'a', 'x', 'x', 'j', 'u', 'n', 'm', 'c', 'n', 'm', 'c', 'w', 'q', 'j', 'w', 'k', 'v', 'n', 'j', 'a', 't', 'w', 'a']

## Sixth step: Frequency Analysis for first/second/third/fourth/fifth letter

For the first letter frequencies are:

	a repeats 9
	b repeats 13
	c repeats 8
	d repeats 1
	e repeats 4
	f repeats 1
	g repeats 1
	i repeats 13
	j repeats 2
	k repeats 3
	l repeats 4
	m repeats 14
	n repeats 6
	o repeats 3
	p repeats 4
	q repeats 11
	t repeats 3
	u repeats 2
	v repeats 9
	w repeats 8
	x repeats 2
	z repeats 4

For the second letter frequencies are:

	b repeats 1
	c repeats 6
	d repeats 5
	e repeats 12
	f repeats 16
	g repeats 1
	i repeats 7
	j repeats 7
	k repeats 15
	l repeats 1
	m repeats 1
	n repeats 1
	p repeats 4
	r repeats 7
	s repeats 1
	t repeats 2
	u repeats 6
	v repeats 9
	w repeats 2
	x repeats 4
	y repeats 12

For the third letter frequencies are:

	a repeats 6
	b repeats 8
	c repeats 7
	d repeats 1
	f repeats 4
	g repeats 7
	h repeats 10
	i repeats 5
	j repeats 1
	k repeats 3
	m repeats 3
	o repeats 10
	q repeats 4
	r repeats 4
	s repeats 19
	t repeats 1
	u repeats 2
	v repeats 14
	w repeats 7
	y repeats 1
	z repeats 7

For the fourth letter frequencies are:

	a repeats 14
	b repeats 9
	c repeats 3
	e repeats 4
	f repeats 4
	g repeats 11
	h repeats 4
	i repeats 1
	j repeats 3
	l repeats 1
	n repeats 13
	p repeats 1
	q repeats 5
	r repeats 21
	s repeats 4
	t repeats 1
	u repeats 10
	v repeats 5
	x repeats 3
	y repeats 5
	z repeats 2

For the fifth letter frequencies are:

	a repeats 7
	b repeats 7
	c repeats 10
	d repeats 2
	e repeats 1
	f repeats 2
	h repeats 2
	j repeats 10
	k repeats 3
	l repeats 1
	m repeats 9
	n repeats 17
	o repeats 4
	q repeats 8
	r repeats 5
	t repeats 2
	u repeats 4
	v repeats 4
	w repeats 11
	x repeats 15

Most repeated cipher letters

	1 letter = b, i, m, q
	2 letter = f, k, y
	3 letter = s, v, o, h 
	4 letter = r, a, n, g
	5 letter = n, x, w, j, c


## Seventh step: Decrypt using Vigenere Table

![image](https://user-images.githubusercontent.com/48418580/137532410-9e491566-5645-415a-a975-0bfadddcabb4.png)

Since **e** letter is the most frequent letter in english alphabet, we puzzle into which cipher letter it has transformed and from there decode the text. 
We continue until we get something meaningful as a key. The assumption **IRONJ** was after all correct. 
The cipher letters that "gave the correct key" are:

	letter 1 => m - e = 8 -> i
	letter 2 => v - e = 18 -> r
	letter 3 => s - e = 14 -> o
	letter 4 => r - e = 13 -> n
	letter 5 => n - e = 9 -> j

Once you have a key you can decypher the text:

	ONCE UPON A TIME THERE LIVED A KING WHO HAD A GREAT FOREST NEAR HIS PALACE FULL OF ALL KINDS OF WILD ANIMALS. ONE DAY HE SENT OUT A HUNTSMAN TO SHOOT HIM A ROE BUT HE DID NOT COMEBACK. PERHAPS SOME ACCIDENT HAS BEFALLEN HIM SAID THE KING AND THE NEXT DAY HE SENT OUT TWO MORE HUNTSMEN WHO WERE TO SEARCH FOR HIM BUT THEY TOO STAYED AWAY THE NON. THE THIRD DAY HE SENT FOR ALL HIS HUNTSMEN AND SAID SCOUR THE WHOLE FOREST THROUGH AND DO NOT GIVE UP UNTIL YE HAVE FOUND ALL THREE. BUT OF THE SEALSON ONE CAME HOME AGAIN AND OF THE PACK OF HOUNDS WHICH THEY HAD TAKEN WITH THEM NONE WERE SEEN MORE FROM THAT TIME FORTH. NO ONE WOULD ANY LO
