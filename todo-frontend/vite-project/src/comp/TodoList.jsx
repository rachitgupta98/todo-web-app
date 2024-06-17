/* eslint-disable react/prop-types */
import TodoItem from './TodoItem';

const TodoList = ({ todos, editTodo, deleteTodo, cancelTodo }) => {
    const priorities = ['LOW', 'MEDIUM', 'HIGH'];

  const activeTodos = todos.filter(todo => todo.isActive);
  const inactiveTodos = todos.filter(todo => !todo.isActive);

  const sortedActiveTodos = activeTodos.sort((a, b) => priorities.indexOf(b.priority) - priorities.indexOf(a.priority));
  const sortedInactiveTodos = inactiveTodos.sort((a, b) => priorities.indexOf(b.priority) - priorities.indexOf(a.priority));

  return (
    <div className="todo-list">
      <div className="active-todos">
        <h2 className='header'>Active Tasks</h2>
        {sortedActiveTodos.map(todo => (
          <TodoItem
            key={todo.id}
            todo={todo}
            editTodo={editTodo}
            deleteTodo={deleteTodo}
            cancelTodo={cancelTodo}
          />
        ))}
      </div>
      <div className="cancelled-todos">
        <h2 className='header'>Cancelled Tasks</h2>
        {sortedInactiveTodos.map(todo => (
          <TodoItem
            key={todo.id}
            todo={todo}
            editTodo={editTodo}
            deleteTodo={deleteTodo}
          />
        ))}
      </div>
    </div>
  );
};

export default TodoList;
