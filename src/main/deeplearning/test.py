import numpy as np
import pandas as pd
from keras.preprocessing.text import Tokenizer
import os
import data_preprocessing

#경로
DATA_IN_PATH = './'

#데이터 불러오기
df = pd.read_csv('test_bag.csv')
print(df.head())
print('전체 학습데이터의 개수: {}'.format(len(df)))

#데이터 최대 길이와 가장 많은 단어 수 확인
train_length = df['text'].apply(len)
print(df['text'])
print('text 길이 최대 값: {}'.format(np.max(train_length)))
train_word_counts = df['text'].apply(lambda x: len(x.split(' ')))
print('text 단어 개수 최대 값: {}'.format(np.max(train_word_counts)))

token = Tokenizer()
result = []

#데이터 전처리 시작
#데이터의 text부분을 따로따로 떼네어 result에 넣기
for text in df['text']:
    pre_processing = data_preprocessing.preprocessing(text)
    result.append(pre_processing)

#1차 전처리 후 데이터를 다시 데이터 프레임에 맞춰 넣기
clean_train_df = pd.DataFrame({'text': result, 'category': df['category']})
print(clean_train_df)

#전처리가 끝난 데이터 저장
TRAIN_CLEAN_DATA = 'train_clean.csv'

if not os.path.exists(DATA_IN_PATH):
    os.makedirs(DATA_IN_PATH)

#저장
clean_train_df.to_csv(DATA_IN_PATH + TRAIN_CLEAN_DATA, index = False)
