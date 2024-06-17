import { useState, useEffect } from 'react';
import axios from 'axios';
import TodoForm from './comp/TodoForm';
import TodoList from './comp/TodoList';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const App = () => {
  const [todos, setTodos] = useState([]);
  const [editingTodo, setEditingTodo] = useState(null);
  const [notified, setNotified] = useState(false);

  useEffect(() => {
    const checkDueDates = () => {
      todos.forEach(todo => {
        const dueDate = new Date(todo.dueDate);
        const currentDate = new Date();

        if (currentDate > dueDate && !notified && todo.isActive) {
          toast.info(`Due date reached for: ${todo.title}. Mark it cancel or edit`, {
            autoClose: 5000,
          });
          setNotified(true)
        }
      });
    };
    checkDueDates();
    setInterval(() => {
      checkDueDates();
    }, 60000);
  }, [todos, notified]);

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const response = await axios.get('http://localhost:8989/plivo/api/task');
      setTodos(response.data);
    } catch (error) {
      console.error('Error fetching todos:', error);
    }
  };

  const saveTodo = async (todo) => {
    try {
      if (todo.id) {
        await axios.put(`http://localhost:8989/plivo/api/task?id=${todo.id}`, todo);
      } else {
        await axios.post('http://localhost:8989/plivo/api/task', todo);
      }
      fetchTodos();
      setEditingTodo(null);
    } catch (error) {
      console.error('Error saving todo:', error);
    }
  };

  const deleteTodo = async (id) => {
    try {
      await axios.delete(`http://localhost:8989/plivo/api/task?id=${id}`);
      fetchTodos();
    } catch (error) {
      console.error('Error deleting todo:', error);
    }
  };

  const editTodo = (todo) => {
    setEditingTodo(todo);
  };

  const cancelTodo = async (id) => {
    try {
      const todo = todos.find((t) => t.id === id);
      todo.isActive = false;
      await axios.put(`http://localhost:8989/plivo/api/task?id=${id}`, todo);
      fetchTodos();
    } catch (error) {
      console.error('Error cancelling todo:', error);
    }
  };
  return (
    <div className="app">
      <h1 className='header'>Todo List</h1>
      <TodoForm saveTodo={saveTodo} editingTodo={editingTodo} />
      <TodoList
        todos={todos}
        editTodo={editTodo}
        deleteTodo={deleteTodo}
        cancelTodo={cancelTodo}
      />
      <ToastContainer/>
    </div>
  );
};

export default App;
