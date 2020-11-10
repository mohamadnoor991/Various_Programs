import random
secret=random.randint(0,9)
st="The secret number is " + str(secret) + " : "
i=5
t=True
while i>0 and t==True:
    inp=input("enter your gussing number ")
    if int(inp)==int(secret):
        print("your gussing is write " )
        t=False
        #print(st )
    else:
     print(" your gussing is wrong , the wright on is " + str(secret)+ " try again : ")
     
     
     print ("you still have "+ str(i) +" trying possible") 
     i-=1