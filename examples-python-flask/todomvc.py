from flask import Flask, render_template, request, redirect, url_for
import uuid

class Task:
    def __init__(self, task, status='active'):
        self.id = uuid.uuid1().hex
        self.task = task
        self.status = status

    def toggle(self):
        if self.status == 'active':
            self.status = 'completed'
        else:
            self.status = 'active'

# Global state, yay!
tasks = {}

app = Flask(__name__)
app.debug = True

@app.route('/')
def index():
    return render_template('index.html', tasks=tasks)

@app.route('/todos', methods = ['POST'])
def todos():
    task = Task(task=request.form['item-text'])
    tasks[task.id] = task
    return redirect(url_for('index'))

@app.route('/delete', methods = ['POST'])
def delete_todos():
    del(tasks[request.form['id']])
    return redirect(url_for('index'))

@app.route('/toggle', methods = ['POST'])
def toggle_todo():
    tasks[request.form['id']].toggle()
    return redirect(url_for('index'))

if __name__ == '__main__':
    app.run()
