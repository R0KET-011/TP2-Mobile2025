# serveur.py
from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/api', methods=['GET'])
def api():
    return jsonify({"message": "Bonjour à partir du serveur local!"})
    
if __name__ == '__main__':
    # Démarre le serveur à l'adresse IP 0.0.0.0 pour qu'il soit accessible à l'émulateur
    app.run(host='0.0.0.0', port=5000)