package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Ocorrencia {
    private static int contador = 1;
    private int id;
    private String nomeCidadao;
    private String tipo;
    private String endereco;
    private String status;
    private String descricao;
    private String dataHora;

    public Ocorrencia(String nomeCidadao, String tipo, String endereco, String descricao) {
        this.id = contador++;
        this.nomeCidadao = nomeCidadao;
        this.tipo = tipo;
        this.endereco = endereco;
        this.descricao = descricao;  // Corrigido aqui
        this.status = "Pendente";
        this.dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    // Getters e setters seguem iguais



    public int getId() { return id; }
    public String getNomeCidadao() { return nomeCidadao; }
    public String getTipo() { return tipo; }
    public String getEndereco() { return endereco; }
    public String getDescricao() { return descricao; }
    public String getStatus() { return status; }
    public String getDataHora() { return dataHora; }
    public void setStatus(String novoStatus) { this.status = novoStatus; }
}

class JPanelComBackground extends JPanel {
    private Image backgroundImage;

    public JPanelComBackground(String caminhoImagem) {
        backgroundImage = new ImageIcon(caminhoImagem).getImage();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class SicDigitalModern {
    private static List<Ocorrencia> ocorrencias = new ArrayList<>();
    private static JFrame framePrincipal;
    private static JPanel painelConteudo;

    private static final Color AZUL_ESCURO = new Color(10, 43, 98);
    private static final Color CINZA_CLARO = new Color(245, 247, 250);
    private static final Color CINZA_MEDIO = new Color(200, 204, 209);
    private static final Color CINZA_ESCURO = new Color(220, 220, 220);
    private static final Color BRANCO = Color.WHITE;
    private static final Color VERDE = new Color(0, 123, 85);
    private static final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 30);
    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 20);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SicDigitalModern::exibirLogin);
    }

    private static void exibirLogin() {
        JFrame loginFrame = new JFrame("Login - SIC Digital");
        loginFrame.setSize(500, 700);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);
        loginFrame.getContentPane().setBackground(CINZA_CLARO);
        loginFrame.setLayout(new BorderLayout());

        ImageIcon iconOriginal = new ImageIcon("app/image-removebg-preview.png");
        Image imagemRedimensionada = iconOriginal.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        ImageIcon iconRedimensionado = new ImageIcon(imagemRedimensionada);

        JLabel lblTitulo = new JLabel(iconRedimensionado, JLabel.CENTER);
        lblTitulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        loginFrame.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelForm = new JPanel();
        painelForm.setBackground(BRANCO);
        painelForm.setBorder(new EmptyBorder(20, 30, 20, 30));
        painelForm.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblUsuario = new JLabel("Usuário");
        lblUsuario.setFont(FONT_NORMAL);
        lblUsuario.setForeground(AZUL_ESCURO);
        painelForm.add(lblUsuario, gbc);

        gbc.gridy++;
        JTextField txtUsuario = new JTextField(20);
        txtUsuario.setFont(FONT_NORMAL);
        painelForm.add(txtUsuario, gbc);

        gbc.gridy++;
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(FONT_NORMAL);
        lblSenha.setForeground(AZUL_ESCURO);
        painelForm.add(lblSenha, gbc);

        gbc.gridy++;
        JPasswordField txtSenha = new JPasswordField(20);
        txtSenha.setFont(FONT_NORMAL);
        painelForm.add(txtSenha, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(25, 0, 0, 0);

        JButton btnEntrar = criarBotaoSIC("Entrar", VERDE);
        painelForm.add(btnEntrar, gbc);

        loginFrame.add(painelForm, BorderLayout.CENTER);

        btnEntrar.addActionListener(e -> {
            String usuario = txtUsuario.getText().trim();
            String senha = new String(txtSenha.getPassword());

            if (usuario.equals("admin") && senha.equals("1234")) {
                loginFrame.dispose();
                iniciarSistema();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Usuário ou senha incorretos!", "Erro de autenticação", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.setVisible(true);
    }

    private static JButton criarBotaoSIC(String texto, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setFont(FONT_NORMAL.deriveFont(Font.BOLD));
        botao.setForeground(BRANCO);
        botao.setBackground(corFundo);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(true);
        botao.setBorderPainted(false);
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(corFundo.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(corFundo);
            }
        });
        return botao;
    }

    private static void iniciarSistema() {
        framePrincipal = new JFrame("SIC Digital - Prefeitura Municipal");
        framePrincipal.setSize(1200, 800);
        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framePrincipal.setLocationRelativeTo(null);
        framePrincipal.setLayout(new BorderLayout());

        JPanel painelSidebar = new JPanel();
        painelSidebar.setBackground(CINZA_MEDIO);
        painelSidebar.setPreferredSize(new Dimension(260, 0));
        painelSidebar.setLayout(new GridBagLayout());

        GridBagConstraints gbcSidebar = new GridBagConstraints();
        gbcSidebar.insets = new Insets(15, 20, 15, 20);
        gbcSidebar.fill = GridBagConstraints.HORIZONTAL;
        gbcSidebar.gridx = 0;

        ImageIcon iconOriginal = new ImageIcon("app/image-removebg-preview.png");
        Image imagemRedimensionada = iconOriginal.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        ImageIcon iconRedimensionado = new ImageIcon(imagemRedimensionada);

        JLabel lblLogo = new JLabel(iconRedimensionado);
        lblLogo.setHorizontalAlignment(JLabel.CENTER);
        lblLogo.setBorder(new EmptyBorder(30, 0, 30, 0));

        gbcSidebar.gridy = 0;
        painelSidebar.add(lblLogo, gbcSidebar);

        JButton btnDashboard = criarBotaoSIC("Página Inicial", AZUL_ESCURO.brighter());
        gbcSidebar.gridy = 1;
        painelSidebar.add(btnDashboard, gbcSidebar);

        JButton btnGerar = criarBotaoSIC("Gerar Ocorrência", AZUL_ESCURO.brighter());
        gbcSidebar.gridy = 2;
        painelSidebar.add(btnGerar, gbcSidebar);

        JButton btnListar = criarBotaoSIC("Listar Ocorrências", AZUL_ESCURO.brighter());
        gbcSidebar.gridy = 3;
        painelSidebar.add(btnListar, gbcSidebar);

        JButton btnAlterarStatus = criarBotaoSIC("Alterar Status", AZUL_ESCURO.brighter());
        gbcSidebar.gridy = 4;
        painelSidebar.add(btnAlterarStatus, gbcSidebar);

        JButton btnSair = criarBotaoSIC("Sair", new Color(180, 0, 0));
        gbcSidebar.gridy = 5;
        painelSidebar.add(btnSair, gbcSidebar);

        framePrincipal.add(painelSidebar, BorderLayout.WEST);

        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(AZUL_ESCURO);
        painelTopo.setPreferredSize(new Dimension(0, 80));
        painelTopo.setLayout(new BorderLayout());

        JLabel lblTituloTopo = new JLabel("Sistema de Protocolo e Atendimento ao Cidadão");
        lblTituloTopo.setFont(FONT_TITULO);
        lblTituloTopo.setForeground(BRANCO);
        lblTituloTopo.setBorder(new EmptyBorder(10, 20, 10, 0));

        painelTopo.add(lblTituloTopo, BorderLayout.WEST);
        framePrincipal.add(painelTopo, BorderLayout.NORTH);

        painelConteudo = new JPanelComBackground("app/men-with-tablet-working-logistic.jpg");
        painelConteudo.setLayout(new BorderLayout());
        painelConteudo.setBorder(new EmptyBorder(30, 30, 30, 30));
        framePrincipal.add(painelConteudo, BorderLayout.CENTER);

        btnDashboard.addActionListener(e -> trocarPainel(mostrarPainelDashboard()));
        btnGerar.addActionListener(e -> trocarPainel(mostrarPainelGerar()));
        btnListar.addActionListener(e -> trocarPainel(mostrarPainelListar()));
        btnAlterarStatus.addActionListener(e -> trocarPainel(mostrarPainelAlterarStatus()));

        btnSair.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(framePrincipal, "Deseja realmente sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                framePrincipal.dispose();
                exibirLogin();
            }
        });

        trocarPainel(mostrarPainelDashboard());

        framePrincipal.setVisible(true);
    }

    private static void trocarPainel(JPanel novoPainel) {
        painelConteudo.removeAll();
        painelConteudo.add(novoPainel, BorderLayout.CENTER);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }

    private static JPanel criarPainelCinzaBase() {
        JPanel base = new JPanel();
        base.setBackground(CINZA_ESCURO);
        base.setLayout(new GridBagLayout());
        base.setBorder(new EmptyBorder(20, 20, 20, 20));
        return base;
    }

    // Painel Dashboard modificado para ficar vazio (sem painel cinza e sem mensagem)
    private static JPanel mostrarPainelDashboard() {
        JPanel painel = new JPanel();
        painel.setOpaque(false); // transparente para mostrar o background da imagem
        painel.setLayout(new BorderLayout());
        return painel;
    }

    private static JPanel mostrarPainelGerar() {
    JPanel painel = criarPainelCinzaBase();

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 0, 20, 0);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel lblTitulo = new JLabel("Gerar Nova Ocorrência");
    lblTitulo.setFont(FONT_TITULO);
    lblTitulo.setForeground(AZUL_ESCURO);
    painel.add(lblTitulo, gbc);

    gbc.gridy++;
    JTextField txtNome = new JTextField(20);
    txtNome.setFont(FONT_NORMAL);
    txtNome.setBorder(BorderFactory.createTitledBorder("Nome do Cidadão"));
    painel.add(txtNome, gbc);

    gbc.gridy++;
    String[] tipos = {"Denúncia", "Solicitação", "Reclamação"};
    JComboBox<String> cbTipo = new JComboBox<>(tipos);
    cbTipo.setFont(FONT_NORMAL);
    cbTipo.setBorder(BorderFactory.createTitledBorder("Tipo de Ocorrência"));
    painel.add(cbTipo, gbc);

    gbc.gridy++;
    JTextField txtEndereco = new JTextField(20);
    txtEndereco.setFont(FONT_NORMAL);
    txtEndereco.setBorder(BorderFactory.createTitledBorder("Endereço"));
    painel.add(txtEndereco, gbc);

    // Nova caixinha para descrição (JTextArea dentro de JScrollPane)
    gbc.gridy++;
    JTextArea txtDescricao = new JTextArea(4, 20);
    txtDescricao.setFont(FONT_NORMAL);
    txtDescricao.setLineWrap(true);
    txtDescricao.setWrapStyleWord(true);
    JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
    scrollDescricao.setBorder(BorderFactory.createTitledBorder("Descrição"));
    painel.add(scrollDescricao, gbc);

    gbc.gridy++;
    JButton btnEnviar = criarBotaoSIC("Registrar Ocorrência", VERDE);
    painel.add(btnEnviar, gbc);

    btnEnviar.addActionListener(e -> {
        String nome = txtNome.getText().trim();
        String tipo = (String) cbTipo.getSelectedItem();
        String endereco = txtEndereco.getText().trim();
        String descricao = txtDescricao.getText().trim();

        if (nome.isEmpty() || endereco.isEmpty() || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(framePrincipal, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Ocorrencia novaOcorrencia = new Ocorrencia(nome, tipo, endereco, descricao);
        ocorrencias.add(novaOcorrencia);
        JOptionPane.showMessageDialog(framePrincipal, "Ocorrência registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        txtNome.setText("");
        txtEndereco.setText("");
        txtDescricao.setText("");
        cbTipo.setSelectedIndex(0);
    });

    return painel;
}


    private static JPanel mostrarPainelListar() {
    JPanel painel = criarPainelCinzaBase();
    painel.setLayout(new BorderLayout());

    JLabel lblTitulo = new JLabel("Lista de Ocorrências");
    lblTitulo.setFont(FONT_TITULO);
    lblTitulo.setForeground(AZUL_ESCURO);
    lblTitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
    painel.add(lblTitulo, BorderLayout.NORTH);

    // Adiciona a coluna "Descrição"
    String[] colunas = {"ID", "Nome Cidadão", "Tipo", "Endereço", "Descrição", "Status", "Data/Hora"};
    DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // tabela somente leitura
        }
    };

    JTable tabela = new JTable(modeloTabela);
    tabela.setFont(FONT_NORMAL);
    tabela.setRowHeight(25);
    tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    JTableHeader cabecalho = tabela.getTableHeader();
    cabecalho.setFont(FONT_NORMAL.deriveFont(Font.BOLD));
    cabecalho.setBackground(AZUL_ESCURO);
    cabecalho.setForeground(BRANCO);

    JScrollPane scroll = new JScrollPane(tabela);
    painel.add(scroll, BorderLayout.CENTER);

    // Carregar dados
    modeloTabela.setRowCount(0);
    for (Ocorrencia o : ocorrencias) {
        modeloTabela.addRow(new Object[] {
            o.getId(),
            o.getNomeCidadao(),
            o.getTipo(),
            o.getEndereco(),
            // Para evitar poluir a tabela, podemos limitar o texto da descrição na tabela (ex: 30 chars)
            o.getDescricao().length() > 30 ? o.getDescricao().substring(0, 30) + "..." : o.getDescricao(),
            o.getStatus(),
            o.getDataHora()
        });
    }

    // Evento para abrir popup com a descrição completa ao clicar na linha
    tabela.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {  // duplo clique abre o popup
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada != -1) {
                    // A linha selecionada pode estar ordenada, então pegar o id real
                    int idOcorrencia = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
                    Ocorrencia oc = null;
                    for (Ocorrencia o : ocorrencias) {
                        if (o.getId() == idOcorrencia) {
                            oc = o;
                            break;
                        }
                    }
                    if (oc != null) {
                        JTextArea areaDescricao = new JTextArea(oc.getDescricao());
                        areaDescricao.setLineWrap(true);
                        areaDescricao.setWrapStyleWord(true);
                        areaDescricao.setEditable(false);
                        areaDescricao.setFont(FONT_NORMAL);
                        JScrollPane scrollDesc = new JScrollPane(areaDescricao);
                        scrollDesc.setPreferredSize(new Dimension(400, 200));

                        JOptionPane.showMessageDialog(framePrincipal, scrollDesc, 
                            "Descrição Completa - Ocorrência ID " + oc.getId(),
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    });

    return painel;
}


    private static JPanel mostrarPainelAlterarStatus() {
        JPanel painel = criarPainelCinzaBase();
        painel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblTitulo = new JLabel("Alterar Status da Ocorrência");
        lblTitulo.setFont(FONT_TITULO);
        lblTitulo.setForeground(AZUL_ESCURO);
        painel.add(lblTitulo, gbc);

        gbc.gridy++;
        JTextField txtId = new JTextField(10);
        txtId.setFont(FONT_NORMAL);
        txtId.setBorder(BorderFactory.createTitledBorder("ID da Ocorrência"));
        painel.add(txtId, gbc);

        gbc.gridy++;
        String[] opcoesStatus = {"Pendente", "Em Andamento", "Concluído"};
        JComboBox<String> cbStatus = new JComboBox<>(opcoesStatus);
        cbStatus.setFont(FONT_NORMAL);
        cbStatus.setBorder(BorderFactory.createTitledBorder("Novo Status"));
        painel.add(cbStatus, gbc);

        gbc.gridy++;
        JButton btnAtualizar = criarBotaoSIC("Atualizar Status", VERDE);
        painel.add(btnAtualizar, gbc);

        btnAtualizar.addActionListener(e -> {
            String idTexto = txtId.getText().trim();
            if (idTexto.isEmpty()) {
                JOptionPane.showMessageDialog(framePrincipal, "Informe o ID da ocorrência!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idTexto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(framePrincipal, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Ocorrencia ocorrenciaEncontrada = null;
            for (Ocorrencia o : ocorrencias) {
                if (o.getId() == id) {
                    ocorrenciaEncontrada = o;
                    break;
                }
            }

            if (ocorrenciaEncontrada == null) {
                JOptionPane.showMessageDialog(framePrincipal, "Ocorrência não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String novoStatus = (String) cbStatus.getSelectedItem();
            ocorrenciaEncontrada.setStatus(novoStatus);

            JOptionPane.showMessageDialog(framePrincipal, "Status atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            txtId.setText("");
            cbStatus.setSelectedIndex(0);
        });

        return painel;
    }

    
}
