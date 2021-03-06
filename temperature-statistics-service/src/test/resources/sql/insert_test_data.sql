INSERT INTO events (id, sensor_id, type, at, temperature)VALUES
('7EBFD660B49543ADAEFE5C90942B034E', '392E55CCD6F711E79296CEC278B6B50A', 'TEMPERATURE_EXCEEDED', now(), 120.0),
('7EBFD660B49543ADAEFE5C90942B035E', '392E55CCD6F711E79296CEC278B6B50A', 'TEMPERATURE_EXCEEDED', DATEADD('MONTH', -1, now()), 110.0),
('7EBFD660B49543ADAEFE5C90942B036E', '392E55CCD6F711E79296CEC278B6B50A', 'TEMPERATURE_EXCEEDED', DATEADD('DAY', -7, now()), 100.0);

INSERT INTO measurement(id, sensor_id, temperature, at) VALUES
('7EBFD660B49543ADAEFE5C90942B034E', '392E55CCD6F711E79296CEC278B6B50A', 120.0, DATEADD('MINUTE', -1, now())),
('7EBFD660B49543ADAEFE5C90942B035E', '392E55CCD6F711E79296CEC278B6B50A', 110.0, DATEADD('HOUR', -1, now())),
('7EBFD660B49543ADAEFE5C90942B036E', '392E55CCD6F711E79296CEC278B6B50A', 87.1, DATEADD('YEAR', -1, now())),

('aab52830da1511e79296cec278b6b50a', 'ce6c2312eec1445b837d43d66148ff49', 43.8, DATEADD('MINUTE', -20, now())),
('aab52aceda1511e79296cec278b6b50a', 'ce6c2312eec1445b837d43d66148ff49', 110.6, DATEADD('MINUTE', -45, now())),
('aab52bdcda1511e79296cec278b6b50a', 'ce6c2312eec1445b837d43d66148ff49', 34.4, DATEADD('DAY', -3, now())),
('aab52cc2da1511e79296cec278b6b50a', 'ce6c2312eec1445b837d43d66148ff49', 118.2, DATEADD('DAY', -5, now())),
('aab52d9eda1511e79296cec278b6b50a', 'ce6c2312eec1445b837d43d66148ff49', 118.2, DATEADD('DAY', -5, now())),
('aab52e70da1511e79296cec278b6b50a', 'ce6c2312eec1445b837d43d66148ff49', 118.2, DATEADD('DAY', -5, now()));
