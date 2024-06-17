import { useState, useEffect } from 'react';
import axios from 'axios';
import TodoList from './components/TodoList';
import TodoForm from './components/TodoForm';

const App = () => {
  const [todos, setTodos] = useState([]);
  const [editingTodo, setEditingTodo] = useState(null);

  // Fetch todos from the API
  useEffect(() => {
    const fetchTodos = async () => {
      const response = await axios.get('/api/todos'); // Update with your API endpoint
      setTodos(response.data);
    };
    fetchTodos();
  }, []);

  // Add or update a todo
  const saveTodo = async (todo) => {
    if (todo.id) {
      await axios.put(`/api/todos/${todo.id}`, todo); // Update with your API endpoint
      setTodos(todos.map(t => (t.id === todo.id ? todo : t)));
    } else {
      const response = await axios.post('/api/todos', todo); // Update with your API endpoint
      setTodos([...todos, response.data]);
    }
    setEditingTodo(null);
  };

  // Delete a todo
  const deleteTodo = async (id) => {
    await axios.delete(`/api/todos/${id}`); // Update with your API endpoint
    setTodos(todos.filter(todo => todo.id !== id));
  };

  // Edit a todo
  const editTodo = (todo) => {
    setEditingTodo(todo);
  };

  return (
    <div className="App">
      <h1>Todo List</h1>
      <TodoForm saveTodo={saveTodo} editingTodo={editingTodo} />
      <TodoList todos={todos} deleteTodo={deleteTodo} editTodo={editTodo} />
    </div>
  );
};

export default App;
