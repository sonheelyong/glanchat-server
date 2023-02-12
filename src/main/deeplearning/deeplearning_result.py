import numpy as np
from keras.preprocessing.text import Tokenizer
import pandas as pd
from keras.models import load_model
from keras.utils import pad_sequences
import oracledb
import test_h5py
import sys

# email = sys.argv[1]
email = 'test23@gmail.com'


test_text = test_h5py.test_text(email)

print(test_text)

con = oracledb.connect(user="c##oracle_chat", password="1234", dsn="localhost:1521/xe")
cursor = con.cursor()

print(email)

cursor.execute("SELECT * FROM FAVORITE_TB WHERE EMAIL = " + "\'" + email + "\'")
for i in cursor:
    print(i)

token = Tokenizer()

model = load_model('C:\\Users\\82105\\Desktop\\ws\\chat\\chat\\src\\main\\deeplearning\\model\\best_model.hdf5')

test_df = pd.read_csv('C:\\Users\\82105\\Desktop\\ws\\chat\\chat\\src\\main\\deeplearning\\test_text.csv')
df = pd.read_csv('C:\\Users\\82105\\Desktop\\ws\\chat\\chat\\src\\main\\deeplearning\\train_clean.csv')
result = []

for text in df['text']:
    result.append(text)

pred_result = []
last_prediction = []
for i in test_df['text']:
    pred_result.append(i)

print(pred_result)
token.fit_on_texts(result)
token.fit_on_texts(pred_result)

check_result = token.texts_to_sequences(result)
max_num = max(max(check_result))
pred_text = token.texts_to_sequences(pred_result)
view_result = []
pred_text = pad_sequences(pred_text, maxlen=50)
pred_text = np.where(pred_text > max_num, 0, pred_text)

print(pred_text)
Y_prediction = model.(pred_text)

for i in Y_prediction:
    pred_category = list(i).index(max(i))

    if pred_category == 0:
        view_result += ["food"]
    elif pred_category == 1:
        view_result += ["game"]
    elif pred_category == 2:
        view_result += ["movie"]
    elif pred_category == 3:
        view_result += ["music"]
    elif pred_category == 4:
        view_result += ["sports"]
    elif pred_category == 5:
        view_result += ["travel"]

print(view_result)

for i in view_result:
    print(i)
    sql = ""
    sql += "UPDATE FAVORITE_TB SET "
    sql += i
    sql += " = "
    sql += i
    sql += " + 1 WHERE EMAIL = " + "\'" + email + "\'"
    print(sql)
    cursor.execute(sql)
    con.commit()


cursor.execute("SELECT * FROM FAVORITE_TB WHERE EMAIL = \'" + email + "\'")
for i in cursor:
    print(i)

cursor.execute("UPDATE MESSAGE_TB SET MESSAGE_CHECK = \'1\' WHERE SENDER_NAME= \'" + email + "\'and MESSAGE_CHECK= \'0\'")
con.commit()

con.close()

