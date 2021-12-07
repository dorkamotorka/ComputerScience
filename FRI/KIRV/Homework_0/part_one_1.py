#!/usr/bin/python

from collections import Counter

Em = 'ectaraknniigdabhaetufaiuagdlrenaindnehatgrnetdsoadrotehwceihrogbeoedalnpeltpshespeapseelwrlaaewyocnsutaddenauotbthiteemwnehhteebaygngootrwpiiretsaowfutdanhtevyernhgoitnfoheteawgmsoteenhkgneibcemeavrnarygyttiahsdnraodertedhagderereontkpeaewtahlclnhguitnrehdteerttehagderereenstsilhedtsoesnwottacubahtbtuwoteevclolkceohfllsealpeneadtneihmnrnoigonhatefohrteppealssaiwmsnitsghtneehsocdenswnsoaoederrdwottacnaahdtdiimnghttheoeflolaelpseaidtnnhomneriagonntrephapweslagenhotehttnehdroisnffroeeotedkeawcpthtuhbteraegdnarfetitsorwundtlolhtmeiffraoermohseasmorhucdmloehomtiheweovrltsaatcenhostndeeatdenhynumogaaldnihsmliefdnruetteehrewottac'
EEm = 'tcreaannikigbaadhefuitaudgraleiadnnnahgetrtesndordtaoecwihehgoerboadnelptlsephpsaeepeewslraawleyncuostddnaeatotubhetmiewhetnheabgeynoorgtwiieprtoafswuadhtntyvreenogthinhotfeegwsamoeehtnkenbgicemvearragnyyithtasrnodadtrdeehdgraeeoetrnkaewpetlhlacnugthinhetrdetreethdgraeeeesrntlieshdosstentoawtcabtuhbwuttoecvoellechkofslalelneapedenhtimnrinoghntoaehotfreeplpasiamswstignshenhtesdcnoessnawooedrerdtoawtcaadnhtiindmgttehholfleoapleesatdninhnmroeiognantpearhpseawlghnteoettehnhorsdinrfefoeetkodecwtaphhuttbeeadrgnfrtaeioswtrutdonllmtihefareforhoemsaomhsrumdoclemoihthewveorstalatneochsdneteaedhtnymugnoadlianhlmesifrneduteerthetoawtc'

# From the frequence of character I estimate this is a rough distribution - Monoalphabetic encryption
# It is Transposition cipher because the frequence of the chars does not change between encryptions
Em_freq = Counter(list(Em))
EEm_freq = Counter(list(EEm))
#print(Em_freq)
#print(EEm_freq)

# Every 6 letter mixed
# Encryption -> 1 -> 4 -> 5 -> 3 ->
# Decryption <- 1 <- 4 <- 5 <- 3 <-
def decrypt(m):
    words = [m[i:i+6] for i in range(0, len(m), 6)]
    decrypted = ''
    for word in words:
        tmp = word[3] + word[1] + word[0] + word[4] + word[2] + word[5]
        decrypted += tmp

    return decrypted
    
def encrypt(m):
    words = [m[i:i+6] for i in range(0, len(m), 6)]
    encrypted = ''
    for word in words:
        tmp = word[2] + word[1] + word[4] + word[0] + word[3] + word[5]
        encrypted += tmp

    return encrypted

if __name__ == '__main__':
    # 2.
    dec = decrypt(Em)
    print('DECRYPTED: ', dec)
    enc = encrypt(dec)
    assert enc == Em
    enc2 = encrypt(enc)
    assert enc2 == EEm

    # 3.
    enc3 = encrypt(enc2)
    enc4 = encrypt(enc3)
    assert enc4 == dec 
