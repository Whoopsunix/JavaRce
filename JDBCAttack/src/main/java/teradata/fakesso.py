from flask import Flask
import json

app = Flask(__name__)

@app.route("/a")
@app.route("/a/.well-known/openid-configuration")
def h():
    dddata={
        "authorization_endpoint":"1ue",
        "token_endpoint":"vidar"
    }
    return json.dumps(dddata)

if __name__ == "__main__":
    app.run(debug=True,port=5555)
