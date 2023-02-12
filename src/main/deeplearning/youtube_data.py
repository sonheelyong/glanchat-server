# 유튜브에서 데이터 가져오는 코드
import pandas
from googleapiclient.discovery import build

import warnings # 경고창 무시
warnings.filterwarnings('ignore')

comments = list()
api_obj = build('youtube', 'v3', developerKey='AIzaSyBicIo4v7JtJnZ2tK4fSCENAv_Rsq-6Yuw')
response = api_obj.commentThreads().list(part='snippet,replies', videoId='GfJy8Xcb5kQ', maxResults=100).execute()

while response:
    for item in response['items']:
        comment = item['snippet']['topLevelComment']['snippet']
        comments.append(
            [comment['textDisplay']])

        if item['snippet']['totalReplyCount'] > 0:
            for reply_item in item['replies']['comments']:
                reply = reply_item['snippet']
                comments.append(
                    [reply['textDisplay']])

    if 'nextPageToken' in response:
        response = api_obj.commentThreads().list(part='snippet,replies', videoId='GfJy8Xcb5kQ',
                                                 pageToken=response['nextPageToken'], maxResults=100).execute()
    else:
        break

df = pandas.DataFrame(comments)
df.to_csv('results.csv', header=['comment'], index=None)