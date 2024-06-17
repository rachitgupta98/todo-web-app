/* eslint-disable react/prop-types */
import { useState, useEffect } from 'react';

const priorities = ['LOW', 'HIGH', 'MEDIUM'];

const TodoForm = ({ saveTodo, editingTodo }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [dueDate, setDueDate] = useState('');
  const [priority, setPriority] = useState('LOW')
  const [isRecurring, setIsRecurring] = useState(false);
  const [recurrenceType, setRecurrenceType] = useState('');

  useEffect(() => {
    if (editingTodo) {
      setTitle(editingTodo.title);
      setDescription(editingTodo.description);
      setDueDate(editingTodo.dueDate || '');
      setPriority(editingTodo.priority || 'LOW');
      setIsRecurring(editingTodo.isRecurring || false);
      setRecurrenceType(editingTodo.recurrenceType || '');
    }
  }, [editingTodo]);

  const handleSubmit = (e) => {
    e.preventDefault();
    saveTodo({
      id: editingTodo ? editingTodo.id : null,
      title,
      description,
      dueDate,
      priority,
      isActive: true,
      isRecurring,
      recurrenceType
    });
    clearForm();
  };

  const clearForm = () => {
    setTitle('');
    setDescription('');
    setDueDate('');
    setPriority('Low');
    setIsRecurring(false);
    setRecurrenceType('');
    window.location.reload();
  };

  return (
    <form className="form-container" onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
      />
      <textarea
        placeholder="Description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        required
      ></textarea>
      <input
        type="date"
        placeholder="Due Date"
        value={dueDate}
        onChange={(e) => setDueDate(e.target.value)}
      />
      <select
        value={priority}
        onChange={(e) => setPriority(e.target.value)}
      >
        {priorities.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>
      <label>
        <input
          type="checkbox"
          checked={isRecurring}
          onChange={(e) => setIsRecurring(e.target.checked)}
        />
        Recurring Task
      </label>
      {isRecurring && (
          <select
            value={recurrenceType}
            onChange={(e) => setRecurrenceType(e.target.value)}>
            <option value="NEVER">NEVER</option>
            <option value="DAILY">Daily</option>
            <option value="WEEKLY">Weekly</option>
            <option value="MONTHLY">MONTHLY</option>
          </select>
        )}
      <button type="submit">{editingTodo ? 'Update' : 'Add'} Todo</button>
      {editingTodo && (
        <button type="button" onClick={clearForm}>
          Cancel
        </button>
      )}
    </form>
  );
};

export default TodoForm;
