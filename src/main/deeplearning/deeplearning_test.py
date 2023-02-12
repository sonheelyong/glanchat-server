import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from keras.preprocessing.text import Tokenizer
from keras.utils import pad_sequences
from keras.layers import Embedding, Dense, LSTM
from sklearn.model_selection import train_test_split
from keras.models import Sequential
from keras.callbacks import EarlyStopping, ModelCheckpoint

#전처리가 끝난 데이터 불러오기
df = pd.read_csv('train_clean.csv')
token = Tokenizer()
result = []
result_y = []

#데이터의 텍스트 부분만
for text in df['text']:
    result.append(text)

#데이터의 카테고리 부분만
for category in df['category']:
    result_y.append(category)

#text 한 행의 최대 단어 개수 파악
train_word_counts = df['text'].apply(lambda x: len(x.split(' ')))
print('text 단어 개수 최대 값: {}'.format(np.max(train_word_counts)))

#데이터의 텍스트 부분을 토큰화, 인덱스 처리, 총 단어 갯수
token.fit_on_texts(result)
y = token.texts_to_sequences(result)
max_word = len(token.index_word) + 1
print(token.index_word)

#다시 데이터 합치기
clean_train_df = pd.DataFrame({'text': y, 'category': df['category']})

#데이터를 학습셋과 테스트셋으로 분리 학습셋과 테스트셋의 비율은 0.2
X_train, X_test, y_train, y_test = train_test_split(clean_train_df['text'],clean_train_df['category'], test_size=0.2, shuffle=True)


#데이터의 텍스트 부분 길이 맞추기, 데이터 하나의 길이가 50이 넘어가면 버리고, 50이하면 나머지 칸은 0으로 채움
X_train = pad_sequences(X_train, maxlen=50)
X_test = pad_sequences(X_test, maxlen=50)

#데이터의 카테고리 부분 원핫 인코딩
y_train = pd.get_dummies(y_train)
y_test = pd.get_dummies(y_test)

#모델 설정
model = Sequential()
#max_word는 총 단어의 갯수, 50은 X_train과 X_test에서 설정한 단어의 갯수
model.add(Embedding(max_word,50))
#모델은 LSTM을 사용
model.add(LSTM(50, activation='tanh'))
#출력은 카테고리의 갯수인 6개로 설정
model.add(Dense(6, activation='softmax'))

#모델 실행 옵션 설정
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

#저장을 위함
model_path = "./model/best_model.hdf5"
checkpointer = ModelCheckpoint(filepath=model_path, verbose=1, monitor='val_loss', save_best_only=True)

#'val_loss'가 10회 이상 값이 줄어들지 않으면 멈춤
early_stopping_callback = EarlyStopping(monitor='val_loss', patience=10)

#실행 세팅
history = model.fit(X_train, y_train, batch_size=30, epochs=300, validation_data=(X_test, y_test), verbose=0, callbacks=[early_stopping_callback, checkpointer])

y_vloss = history.history['val_loss']
y_loss = history.history['loss']

x_len = np.arange(len(y_loss))
plt.plot(x_len, y_vloss, marker='.', c="red", label='Testset_loss')
plt.plot(x_len, y_loss, marker='.', c="blue", label='Trainset_loss')

plt.legend(loc='upper right')
plt.grid()
plt.xlabel('epoch')
plt.ylabel('loss')
plt.show()

