INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (0, 'Павлюченко', 'Валерий', 'Альбертович', '228-14-88');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (1, 'Голубев', 'Максим', 'Алексеевич', '564-65-98');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (2, 'Соколов', 'Никита', 'Валерьевич', '275-41-28');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (3, 'Уварова', 'Майя', 'Ярославовна', '297-65-35');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (4, 'Воронцов', 'Эдуард', 'Русланович', '478-66-53');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (5, 'Панфилова', 'Элеонора', 'Сергеевна', '365-03-13');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (6, 'Тарасова', 'Ульяна', 'Владимировна', '594-12-02');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (7, 'Максимов', 'Роман', 'Алексеевич', '302-80-50');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (8, 'Михайлов', 'Павел', 'Николаевич', '452-67-39');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (9, 'Копылов', 'Аркадий', 'Авдеевич', '214-39-08');

INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (0, 'Кузнецов', 'Петр', 'Иванович', 'Аллерголог');
INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (1, 'Воронин', 'Максим', 'Глебович', 'Флеболог');
INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (2, 'Сергеев', 'Владимир', 'Станиславович', 'Терапевт');
INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (3, 'Тоцкий', 'Валерий', 'Анатольевич', 'Отоларинголог');
INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (4, 'Семёнов', 'Степан', 'Александрович', 'Венеролог');
INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (5, 'Валерьев', 'Валерьян', 'Валерьевич', 'Терапевт');

INSERT INTO Prescription (Prescription_ID, Description, Patient, Doctor, Creation_Date, Validity, Priority) VALUES (0, 'Аддерал', 0, 2, '2019-01-01', '2020-01-01', 'Немедленный');
INSERT INTO Prescription (Prescription_ID, Description, Patient, Doctor, Creation_Date, Validity, Priority) VALUES (1, 'Антибиотик', 7, 1, '2019-05-07', '2020-05-21', 'Срочный');
INSERT INTO Prescription (Prescription_ID, Description, Patient, Doctor, Creation_Date, Validity, Priority) VALUES (2, 'Противоаллергическое', 3, 0, '2019-02-03', '2019-03-03', 'Нормальный');
INSERT INTO Prescription (Prescription_ID, Description, Patient, Doctor, Creation_Date, Validity, Priority) VALUES (3, 'Капли валерианы', 6, 5, '2018-02-03', '2020-03-03', 'Нормальный');
INSERT INTO Prescription (Prescription_ID, Description, Patient, Doctor, Creation_Date, Validity, Priority) VALUES (4, 'Мирамистин', 9, 4, '2019-05-20', '2019-06-21', 'Срочный');