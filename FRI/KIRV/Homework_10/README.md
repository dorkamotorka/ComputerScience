# Homework 10

## Part One

Za računanje hash-a sem uporabil SHA256 (specifično python module hashlib.sha256). 
Že preverjeno besedo in njen hash sem shranjeval v slovar in s tem sledil, katere besede sem že preveril.
V skladu z rojstnodnevnim paradoksom, za nobeno dolžino besede ni potrebno več kot $\sqrt{(26^{dolžina}})$-krat generirati nove besede, preden je najden trk.
Iz poskušanja lahko zaključim, da za besedo krajšo od 5 črk, nisem uspel najdi trčenja.
Odločil sem se, da poiščem trke za besede dolge od 5-10 črk, pri čemer primerjam zadnjih 12 znakov hasha.

Rezultati:

Collision #1 found
New word with same (truncated) hash:                 tezenj, hex is 74657a656e6a, hash is 488408a07897
Previously stored word with same (truncated) hash:   osywxv, hex is 6f7379777876, hash is 488408a07897
Size of stored (word, hash) pairs for 6-character word until collision found: 19077731

Collision #2 found
New word with same (truncated) hash:                 fhcrhrx, hex is 66686372687278, hash is fb925f29f49f
Previously stored word with same (truncated) hash:   xoshiwc, hex is 786f7368697763, hash is fb925f29f49f
Size of stored (word, hash) pairs for 7-character word until collision found: 12476722

Collision #3 found
New word with same (truncated) hash:                 regpqile, hex is 7265677071696c65, hash is bfb15d9e4de7
Previously stored word with same (truncated) hash:   afrgebsj, hex is 616672676562736a, hash is bfb15d9e4de7
Size of stored (word, hash) pairs for 8-character word until collision found: 16735009

Collision #4 found
New word with same (truncated) hash:                 wnodyyadv, hex is 776e6f647979616476, hash is c8c928336c9b
Previously stored word with same (truncated) hash:   kmaeacdre, hex is 6b6d61656163647265, hash is c8c928336c9b
Size of stored (word, hash) pairs for 9-character word until collision found: 16987452
