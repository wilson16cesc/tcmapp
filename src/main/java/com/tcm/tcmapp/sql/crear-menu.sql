
SELECT TOP 100 * FROM dbo.seg_paginas order by id_padre;

--delete from seg_paginas;

SET IDENTITY_INSERT seg_paginas ON
insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                values (1, 'Item 1',   'http://item1.com', 0,      'save',    0,          1,      getdate(), 'mfigueroa', 0);

                insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                               values (4, 'Item 4',   'http://item4.com', 0,      'save',    1,          1,      getdate(), 'mfigueroa', 0);

                    insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                     values (5, 'Item 5',   'http://item5.com', 0,      'save',    4,          1,      getdate(), 'mfigueroa', 0);

                        insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                         values (7, 'Item 7',   'http://item7.com', 1,      'save',    5,          1,      getdate(), 'mfigueroa', 0);
                        insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                         values (10, 'Item 10',   'http://item10.com', 1,      'save',    5,          1,      getdate(), 'mfigueroa', 0);
                    insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                     values (6, 'Item 6',   'http://item6.com', 0,      'save',    4,          1,      getdate(), 'mfigueroa', 0);
                        insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                         values (8, 'Item 8',   'http://item8.com', 1,      'save',    6,          1,      getdate(), 'mfigueroa', 0);

insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                values (2, 'Item 2',   'http://item2.com', 0,      'save',    0,          1,      getdate(), 'mfigueroa', 0);

                insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                 values (9, 'Item 9',   'http://item9.com', 0,      'save',    2,          1,      getdate(), 'mfigueroa', 0);

                    insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                     values (11, 'Item 11',   'http://item11.com', 1,      'save',    9,          1,      getdate(), 'mfigueroa', 0);
                    insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                     values (12, 'Item 12',   'http://item12.com', 1,      'save',    9,          1,      getdate(), 'mfigueroa', 0);

insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                values (3, 'Item 3',   'http://item3.com', 0,      'save',    0,          1,      getdate(), 'mfigueroa', 0);

                insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                 values (13, 'Item 13',   'http://item13.com', 0,      'save',    3,          1,      getdate(), 'mfigueroa', 0);

                    insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                     values (15, 'Item 15',   'http://item15.com', 1,      'save',    13,          1,      getdate(), 'mfigueroa', 0);
                    insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                     values (16, 'Item 16',   'http://item16.com', 1,      'save',    13,          1,      getdate(), 'mfigueroa', 0);


                insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                 values (14, 'Item 14',   'http://item14.com', 0,      'save',    3,          1,      getdate(), 'mfigueroa', 0);

                    insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                     values (17, 'Item 17',   'http://item17.com', 1,      'save',    14,          1,      getdate(), 'mfigueroa', 0);
                    insert into seg_paginas (id, nombre,    url,                hoja,   icono,      id_padre,   activo, fecha_crea, usuario_crea, nivel)
                                     values (18, 'Item 18',   'http://item18.com', 1,      'save',    14,          1,      getdate(), 'mfigueroa', 0);

SET IDENTITY_INSERT seg_paginas OFF