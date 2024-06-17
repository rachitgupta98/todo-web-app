/* eslint-disable react/prop-types */

const TodoItem = ({ todo, editTodo, deleteTodo, cancelTodo }) => {
  return (
    <div className={`todo-item ${todo.priority.toLowerCase()}`}>
    <h3>{todo.title}</h3>
    <p>{todo.description}</p>
    {todo.dueDate !== "null" && <p className="due-date">Due Date: {todo.dueDate}</p>}
    <p className="priority">Priority: {todo.priority}</p>
    {todo.isRecurring && <p className="recurring">Recurring: {todo.recurrenceType}</p>}
    <button className="editBtn" onClick={() => editTodo(todo)}>Edit</button>
    <button className="deleteBtn" onClick={() => deleteTodo(todo.id)}>Delete</button>
    {todo.isActive &&  <button className="cancelBtn" onClick={() => cancelTodo(todo.id)}>Cancel</button>}
  </div>
  );
};

export default TodoItem;
