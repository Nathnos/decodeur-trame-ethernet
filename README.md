# Décodeur de trames Ethernet

Un projet pour décoder de façon basique des trames ethernet dans un fichier.

Le programme prend en entrée un fichier trace (format texte) contenant les octets ‘bruts’, tels que capturés sur le réseau. Ce fichier pourra contenir plusieurs 
trames Ethernet à la suite (sans préambule ni champ FCS).

[Vidéo de présentation](https://www.youtube.com) (à venir)

## Structure du code

Le code se compose en deux parties : 
- une partie pour extraires les informations pertinents depuis le fichier texte
- une partie d’analyse de la trame, qui sera écrite dans un fichier .txt

### Partie déchiffrage du fichier texte
- On  commence par lire le fichier en entier, en retirant les lignes qui ne commencent pas par un offset valide (au moins 3 symboles hexa, suivis d’un espace). On a alors une liste de lignes.
- On découpe ensuite ces lignes dans les différentes trames, grâce à l’offset. On détecte ici aussi s’il y a des offsets invalides (trames qui commencent au-dessus de 0, offset qui diminue dans une même trame).
- On va rediviser chaque ligne de chaque trame. On regarde si la ligne possède assez d’octets pour que l’offset suivant soit valide, puis on enlève l’offset et la fin de ligne.
- On transforme les symboles hexadécimaux en valeurs numériques (grâce à des fonctions build-in de java).
- On retourne ensuite un wrapper ListeTrames, pour faciliter l’accès aux diverses trames et octets pour la partie analyse.

### Partie analyse
- On prend chaque trame une par une et on la décompose successivement
- On donne la trame entière à notre analyseur Ethernet, qui va en retirer l’en-tête
- On donne ensuite le champ "data" d’Ethernet à l’analyseur IP ; et ainsi de suite, couche après couche.

## Liste des protocoles analyés :
- Couche 2: Ethernet 
- Couche 3: IP 
- Couche 4: UDP 
- Couche 7: DNS et DHCP 

## Format du fichier contenant les trames ethernet (similaire à WireShark) :
- Chaque octet est codé par deux chiffres hexadécimaux.   
- Chaque octet est délimité par un espace.  
- Chaque ligne commence par l’offset du premier octet situé à la suite sur la même 
ligne. L’offset décrit la positon de cet octet dans la trace. 
- Chaque nouvelle trame commence avec un offset de 0 et l’offset est séparé d’un 
espace des octets capturés situés à la suite. 
- L’offset est codé sur plus de deux chiffres hexadécimaux. 
- Les caractères hexadécimaux peuvent être des majuscules ou minuscules. 
- Il n’y a pas de limite concernant la longueur ou le nombre d’octets présents sur 
chaque ligne. 
- Si des valeurs textuelles sont données en fin de ligne, elles doivent être ignorées, 
y compris si ces valeurs sont des chiffres hexadécimaux. 
- Les lignes de texte situées entre les traces ou entrelacées entre les lignes 
d’octets capturés doivent être ignorées.  
- Les lignes d’octets qui ne débutent pas un offset valide doivent être ignorées.  
- Toute ligne incomplète doit être identifiée et soulever une erreur indiquant la 
position de la ligne en erreur.  


### Exemple de trace de trame :
![image](https://user-images.githubusercontent.com/50121403/136543344-a1b96807-7320-4a88-a80e-4b79930b95e0.png)
