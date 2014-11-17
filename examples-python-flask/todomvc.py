from flask import Flask, render_template, request, redirect, url_for
import uuid

# Global state, yay!
tasks = {}

app = Flask(__name__)
app.debug = True

@app.route('/')
def index():
    return render_template('index.html', tasks=tasks)

@app.route('/todos', methods = ['POST'])
def todos():
    tasks[uuid.uuid1().hex] = request.form['item-text']
    return redirect(url_for('index'))

@app.route('/delete', methods = ['POST'])
def delete_todos():
    del(tasks[request.form['id']])
    return redirect(url_for('index'))

if __name__ == '__main__':
    app.run()
