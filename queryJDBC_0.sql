0) retrive outline point
SELECT GLAT,GLON
FROM FILAMENT JOIN OUTLINE  ON name=nameFilament 
WHERE name=?
1) retrive stars
SELECT *
FROM STAR 
2) #retrive stars from rectangle #NB recangle:={center,height,width} #RB LATITUDE ORRIZONTAL..(PARALLELI)
SELECT *
FROM STAR
WHERE glat>center-height/2 AND glat<center+height/2 AND 
	glon>center-width /2 AND glon<center+width /2
