INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (0, '����������', '�������', '�����������', '228-14-88');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (1, '�������', '������', '����������', '564-65-98');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (2, '�������', '������', '����������', '275-41-28');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (3, '�������', '����', '�����������', '297-65-35');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (4, '��������', '������', '����������', '478-66-53');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (5, '���������', '��������', '���������', '365-03-13');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (6, '��������', '������', '������������', '594-12-02');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (7, '��������', '�����', '����������', '302-80-50');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (8, '��������', '�����', '����������', '452-67-39');
INSERT INTO Patient (Patient_ID, Surname, Name, Patronymic, PhoneNumber) VALUES (9, '�������', '�������', '��������', '214-39-08');

INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (0, '��������', '����', '��������', '����������');
INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (1, '�������', '������', '��������', '��������');
INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (2, '�������', '��������', '�������������', '��������');
INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (3, '������', '�������', '�����������', '�������������');
INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (4, '������', '������', '�������������', '���������');
INSERT INTO Doctor (Doctor_ID, Surname, Name, Patronymic, Speciality) VALUES (5, '��������', '��������', '����������', '��������');

INSERT INTO Prescription (Prescription_ID, Description, Patient, Doctor, Creation_Date, Validity, Priority) VALUES (0, '�������', 0, 2, '2019-01-01', '2020-01-01', '�����������');
INSERT INTO Prescription (Prescription_ID, Description, Patient, Doctor, Creation_Date, Validity, Priority) VALUES (1, '����������', 7, 1, '2019-05-07', '2020-05-21', '�������');
INSERT INTO Prescription (Prescription_ID, Description, Patient, Doctor, Creation_Date, Validity, Priority) VALUES (2, '��������������������', 3, 0, '2019-02-03', '2019-03-03', '����������');
INSERT INTO Prescription (Prescription_ID, Description, Patient, Doctor, Creation_Date, Validity, Priority) VALUES (3, '����� ���������', 6, 5, '2018-02-03', '2020-03-03', '����������');
INSERT INTO Prescription (Prescription_ID, Description, Patient, Doctor, Creation_Date, Validity, Priority) VALUES (4, '����������', 9, 4, '2019-05-20', '2019-06-21', '�������');