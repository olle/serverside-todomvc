class CreateTodos < ActiveRecord::Migration[7.0]
  def change
    create_table :todos do |t|
      t.text :todo
      t.integer :status, default: 0
      t.boolean :editing

      t.timestamps
    end
  end
end
