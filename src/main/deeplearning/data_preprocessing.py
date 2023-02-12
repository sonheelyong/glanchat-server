from soynlp.normalizer import *
from konlpy.tag import *
from keras.preprocessing.text import Tokenizer
import re

hannanum = Hannanum()
okt = Okt()
token = Tokenizer()

#데이터 전처리
def preprocessing(x):

    #데이터를 주로 유튜브에서 크롤링해왔기에 괄호와 괄호 안에 주소값이나, 영상 시간이 있는 경우가 있어 처리
    pattern = r'\([^)]*\)'  # ()
    x = re.sub(pattern=pattern, repl='', string=x)

    pattern = r'\[[^)]*\]'  # []
    x = re.sub(pattern=pattern, repl='', string=x)

    pattern = r'\<[^)]*\>'  # <>
    x = re.sub(pattern=pattern, repl='', string=x)

    pattern = r'\{[^)]*\}'  # {}
    y = re.sub(pattern=pattern, repl='', string=x)
    
    #데이터에서 특수문자와 숫자는 데이터에서 혼란을 초래할 수 있기에 삭제
    y = re.sub(r"[^\uAC00-\uD7A3\s]","",y)
    
    #데이터의 앞 뒤 공백 없애기 위의 데이터에서 공백값으로 변환하다 보니 앞뒤에 공백이 생기므로 앞뒤 공백 삭제
    check = y
    y = y.strip()
    #데이터에서 동사보다는 명사가 그 주제를 더 잘 나타낼 거라고 판단 하기에 명사만을 출력
    y = hannanum.nouns(check)

    #한 글자만 있는 것은 필요 없는 데이를 너무 많이 뽑아낼 수 있기 때문에 삭제
    s_list = []
    for word in y:
        if len(word) != 1:
            s_list.append(word)

    return s_list
