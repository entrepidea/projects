import socket
import sys
import requests
import requests_oauthlib
import json

ACCESS_TOKEN = '837148831183699968-9xlUK5OUc5Z48FjrZO8SFEQ1hUTBcEl'
ACCESS_SECRET = '54allOL5ZEsSzbPlfsFxUX7RU7OcdY8pto38HhoFK9gF3'
CONSUMER_KEY = 'qNtZOgLp4Q4C23j0a0ADSshFP'
CONSUMER_SECRET = '4x0o8s719G2cQqHfFoPTMpPcQQjJkLIgToLZvuFhCUUA34AXwf'
my_auth = requests_oauthlib.OAuth1(CONSUMER_KEY, CONSUMER_SECRET,ACCESS_TOKEN, ACCESS_SECRET)


def get_tweets():
    url = 'https://stream.twitter.com/1.1/statuses/filter.json'
    query_data = [('language', 'en'), ('locations', '-130,-20,100,50'),('track','#')]
    query_url = url + '?' + '&'.join([str(t[0]) + '=' + str(t[1]) for t in query_data])
    response = requests.get(query_url, auth=my_auth, stream=True)
    return response


def send_tweets_to_spark(http_resp, tcp_connection):
    for line in http_resp.iter_lines():
        try:
            full_tweet = json.load(line)
            tweet_text = full_tweet['text']
            print('twit text: '+tweet_text)
            print("------------------------------------------")
            tcp_connection.send(tweet_text + '\n')
        except:
            e = sys.exc_info()[0]
            print("Error: %s" % e)


def show_tweets(resp):
    #resp = get_tweets()
    # testing
    for line in resp.iter_lines():
        try:
            full_tweet = json.loads(line)
            tweet_text = full_tweet['text']
            print("Tweet Text: " + tweet_text)
            print("------------------------------------------")
        except:
            e = sys.exc_info()[0]
            print("Error: %s" % e)

"""
Testing cases

"""
def test_get_tweets():
    pass


















def main(argv):
    """
    TCP_IP = "localhost"
    TCP_PORT = 9009
    conn = None
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((TCP_IP, TCP_PORT))
    s.listen(1)
    print("Waiting for TCP connection...")
    conn, addr = s.accept()
    """
    print("Connected... Starting getting tweets.")
    resp = get_tweets()
    show_tweets(resp)
    #send_tweets_to_spark(resp, conn)

if __name__ == '__main__':
    main(sys.argv[:1])
