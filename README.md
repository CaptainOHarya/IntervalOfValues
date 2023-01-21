Описание
Модифицируйте код программы из предыдущей задачи таким образом, чтобы можно было найти максимальный интервал значений среди всех строк 
(для этого вам понадобится использовать Callable, Future и пул потоков)

Реализация
Отведите ветку max в репозитории задачи "Интервал значений".
Заведите пул потоков. Вместо списка из потоков сделайте список из Future.
В цикле отправьте в пул потоков задачи на исполнение, получив в ответ на каждую отправку Future, которые войдут в список.
После цикла с отправкой задач на исполнение пройдитесь циклом по Future и у каждого вызовите get для ожидания и получения результата, 
который вы обработаете для получения ответа на задачу.
В качестве решения на проверку отправьте ссылку на ветку max в вашем репозиториии с решением.