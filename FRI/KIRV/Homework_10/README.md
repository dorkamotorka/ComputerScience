# Homework 10

## Part One

Za računanje hash-a sem uporabil SHA256 (specifično python module hashlib.sha256). 
Že preverjeno besedo in njen hash sem shranjeval v slovar in s tem sledil, katere besede sem že preveril.
V skladu z rojstnodnevnim paradoksom, za nobeno dolžino besede ni potrebno več kot $\sqrt{(26^{dolžina}})$-krat generirati nove besede, preden je najden trk.
Iz poskušanja lahko zaključim, da za besedo krajšo od 5 črk, nisem uspel najdi trčenja.
Ko sem poskušal iskati hash za 48 oz. 40 bitni del hash-a, mi je zmanjkalo pomnilnika. Tako sem se odločil, da poiščem trke za besede dolge od 5-10 črk, pri čemer primerjam zadnjih 10 bitov hasha.

Rezultati:

Collision #1 found
New word with same (truncated) hash:                 bvhem, hex is 627668656d, hash is 3933171f0e
Previously stored word with same (truncated) hash:   uuszk, hex is 7575737a6b, hash is 3933171f0e
Size of stored (word, hash) pairs for 5-character word until collision found: 2068309

Collision #2 found
New word with same (truncated) hash:                 hqaxux, hex is 687161787578, hash is d8d158c3af
Previously stored word with same (truncated) hash:   ylnphx, hex is 796c6e706878, hash is d8d158c3af
Size of stored (word, hash) pairs for 6-character word until collision found: 1093940

Collision #3 found
New word with same (truncated) hash:                 ksxwvnb, hex is 6b737877766e62, hash is 2c133b718c
Previously stored word with same (truncated) hash:   obnaqvx, hex is 6f626e61717678, hash is 2c133b718c
Size of stored (word, hash) pairs for 7-character word until collision found: 760760

Collision #4 found
New word with same (truncated) hash:                 eiqkqkzo, hex is 6569716b716b7a6f, hash is 3d284acdb3
Previously stored word with same (truncated) hash:   ejmjuwsr, hex is 656a6d6a75777372, hash is 3d284acdb3
Size of stored (word, hash) pairs for 8-character word until collision found: 1793337

Collision #5 found
New word with same (truncated) hash:                 eictgivrk, hex is 65696374676976726b, hash is d70dac037a
Previously stored word with same (truncated) hash:   jazrktopg, hex is 6a617a726b746f7067, hash is d70dac037a
Size of stored (word, hash) pairs for 9-character word until collision found: 1412789

